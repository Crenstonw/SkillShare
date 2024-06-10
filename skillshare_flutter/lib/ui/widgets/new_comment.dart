import 'package:flutter/material.dart';
import 'package:skillshare_flutter/models/dtos/new_order_message_request.dart';
import 'package:skillshare_flutter/repositories/messages/messages_repository_impl.dart';

class NewComment extends StatefulWidget {
  final String orderId;
  const NewComment({super.key, required this.orderId});

  @override
  State<NewComment> createState() => _NewCommentState();
}

class _NewCommentState extends State<NewComment> {
  final messagesRepository = MessageRepositoryImpl();
  final _formKey = GlobalKey<FormState>();
  final TextEditingController _titleController = TextEditingController();
  final TextEditingController _messageController = TextEditingController();
  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text('New Comment'),
      content: Form(
        key: _formKey,
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            TextFormField(
              controller: _titleController,
              decoration: const InputDecoration(labelText: 'Title'),
              validator: (value) {
                if (value == null || value.isEmpty) {
                  return 'Please, put a title';
                }
                return null;
              },
            ),
            TextFormField(
              controller: _messageController,
              minLines: 3,
              maxLines: null,
              decoration: const InputDecoration(labelText: 'Message'),
              validator: (value) {
                if (value == null || value.isEmpty) {
                  return 'Please, write the message';
                }
                return null;
              },
            ),
          ],
        ),
      ),
      actions: <Widget>[
        TextButton(
          onPressed: () {
            Navigator.of(context).pop();
          },
          child: const Text('Cancel'),
        ),
        TextButton(
          onPressed: () {
            if (_formKey.currentState!.validate()) {
              NewOrderMessageRequest response = NewOrderMessageRequest(
                title: _titleController.text,
                message: _messageController.text,
                orderId: widget.orderId,
              );
              messagesRepository.newOrderMessage(response);
              Navigator.of(context).pop();
            }
          },
          child: const Text('Send'),
        ),
      ],
    );
  }

  @override
  void dispose() {
    _titleController.dispose();
    _messageController.dispose();
    super.dispose();
  }
}