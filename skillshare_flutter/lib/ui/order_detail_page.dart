import 'package:flutter/material.dart';
import 'package:skillshare_flutter/models/responses/all_order_response.dart';

class OrderDetailPage extends StatefulWidget {
  final Content order;
  const OrderDetailPage({super.key, required this.order});

  @override
  State<OrderDetailPage> createState() => _OrderDetailPageState();
}

class _OrderDetailPageState extends State<OrderDetailPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color.fromARGB(1000, 191, 218, 208),
      body: Column(children: [
        Padding(
          padding: const EdgeInsets.fromLTRB(0, 25, 0, 20),
          child: Container(
            padding: const EdgeInsets.fromLTRB(20, 10, 20, 20),
            width: MediaQuery.of(context).size.width,
            color: Colors.white,
            child:
                Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
              Container(
                width: 100,
                height: 100,
                decoration: BoxDecoration(
                  shape: BoxShape.circle,
                  border: Border.all(color: Colors.black),
                ),
                child: ClipRRect(
                  borderRadius: BorderRadius.circular(50),
                  child: Image.network(
                    widget.order.user.profilePicture,
                    fit: BoxFit.cover,
                  ),
                ),
              ),
              Text(
                widget.order.title,
                style: const TextStyle(fontSize: 40),
              )
            ]),
          ),
        ),
        Padding(
          padding: const EdgeInsets.fromLTRB(0, 0, 0, 20),
          child: Container(
            padding: const EdgeInsets.fromLTRB(20, 10, 20, 20),
            width: MediaQuery.of(context).size.width,
            color: Colors.white,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Text('JOB DESCRIPTION', style: TextStyle(fontSize: 20)),
                Text(widget.order.description)
              ],
            ),
          ),
        ),
        Padding(
          padding: const EdgeInsets.fromLTRB(0, 0, 0, 20),
          child: Container(
            padding: const EdgeInsets.fromLTRB(20, 10, 20, 20),
            width: MediaQuery.of(context).size.width,
            color: Colors.white,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Text('TAGS', style: TextStyle(fontSize: 20)),
                if (widget.order.tags.isNotEmpty)
                  for (int i = 0; i < widget.order.tags.length; i++)
                    Container(
                        color: Colors.amber,
                        decoration: const BoxDecoration(
                            borderRadius:
                                BorderRadius.all(Radius.circular(25))),
                        child: Text(widget.order.description))
                else
                  const Text('No tags avaiable')
              ],
            ),
          ),
        ),
        Padding(
          padding: const EdgeInsets.fromLTRB(0, 0, 0, 20),
          child: Container(
            padding: const EdgeInsets.fromLTRB(20, 10, 20, 20),
            width: MediaQuery.of(context).size.width,
            color: Colors.white,
            child: const Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text('COMMENTS', style: TextStyle(fontSize: 20)),
                Text('WIP')
              ],
            ),
          ),
        )
      ]),
    );
  }
}
