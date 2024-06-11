import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:skillshare_flutter/blocs/myOrders/my_orders_bloc.dart';
import 'package:skillshare_flutter/models/dtos/order_edit_request.dart';
import 'package:skillshare_flutter/models/responses/order_detail_response.dart';
import 'package:skillshare_flutter/models/responses/user_response.dart';
import 'package:skillshare_flutter/repositories/newOrder/new_order_repository.dart';
import 'package:skillshare_flutter/repositories/newOrder/new_order_repository_impl.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository_impl.dart';
import 'package:skillshare_flutter/repositories/user/user_repository.dart';
import 'package:skillshare_flutter/repositories/user/user_repository_impl.dart';
import 'package:skillshare_flutter/ui/widgets/order_list_widget.dart';

class MyOrdersWidget extends StatefulWidget {
  const MyOrdersWidget({super.key});

  @override
  State<MyOrdersWidget> createState() => _MyOrdersWidgetState();
}

class _MyOrdersWidgetState extends State<MyOrdersWidget> {
  late UserResponse me;
  late OrderListRepository orderListRespository;
  late UserRepository userRepository;
  late NewOrderRepository newOrderRepository;
  late MyOrdersBloc _myOrdersBloc;

  String tagString(OrderDetailResponse tags) {
    String result = '';
    for (int i = 0; i < tags.tags.length; i++) {
      if (tags.tags.length - 1 == i) {
        result = '$result${tags.tags[i].name}';
      } else {
        result = '$result${tags.tags[i].name}, ';
      }
    }
    return result;
  }

  List<String> tagUnString(String str) {
    List<String> empty = [];
    if (str.isNotEmpty) {
      return str.split(', ');
    } else {
      return empty;
    }
  }

  void _showNewModal(BuildContext context) {
    final formKey = GlobalKey<FormState>();
    final TextEditingController titleController =
        TextEditingController();
    final TextEditingController descriptionController =
        TextEditingController();
    final TextEditingController valueController =
        TextEditingController();
    final TextEditingController tagsController =
        TextEditingController();

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return SingleChildScrollView(
          child: AlertDialog(
            title: const Text('Edit'),
            content: Form(
              key: formKey,
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: <Widget>[
                  TextFormField(
                    controller: titleController,
                    decoration: const InputDecoration(labelText: 'Title'),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please, put a title';
                      }
                      return null;
                    },
                  ),
                  TextFormField(
                    controller: valueController,
                    keyboardType: TextInputType.number,
                    decoration: const InputDecoration(
                      labelText: 'Enter price',
                      border: OutlineInputBorder(),
                    ),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter a price';
                      }
                      if (double.tryParse(value) == null) {
                        return 'Please enter a valid price';
                      }
                      return null;
                    },
                  ),
                  TextFormField(
                    controller: descriptionController,
                    minLines: 2,
                    maxLines: null,
                    decoration: const InputDecoration(labelText: 'Description'),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please, write the message';
                      }
                      return null;
                    },
                  ),
                  TextFormField(
                    controller: tagsController,
                    minLines: 2,
                    maxLines: null,
                    decoration: const InputDecoration(
                        labelText: 'Tags (must be separated by comas)'),
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
                child: const Text('Close'),
              ),
              TextButton(
                onPressed: () {
                  if (formKey.currentState!.validate()) {
                    OrderEditRequest response = OrderEditRequest(
                        title: titleController.text,
                        description: descriptionController.text,
                        price: double.parse(valueController.text),
                        tags: tagUnString(tagsController.text));
                    newOrderRepository.newOrder(response);
                    Navigator.of(context)
                        .pop();
                        reload();
                  }
                },
                child: const Text('Send'),
              ),
            ],
          ),
        );
      },
    );
  }

  @override
  void initState() {
    orderListRespository = OrderListRepositoryImpl();
    userRepository = UserRepositoryImpl();
    newOrderRepository = NewOrderRepositoryImpl();
    _myOrdersBloc = MyOrdersBloc(orderListRespository, userRepository);
    _myOrdersBloc.add(DoMyOrdersEvent());
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  void reload() {
    orderListRespository = OrderListRepositoryImpl();
    userRepository = UserRepositoryImpl();
    newOrderRepository = NewOrderRepositoryImpl();
    _myOrdersBloc = MyOrdersBloc(orderListRespository, userRepository);
    _myOrdersBloc.add(DoMyOrdersEvent());
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider.value(
      value: _myOrdersBloc,
      child: Scaffold(
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            _showNewModal(context);
          },
          backgroundColor: Colors.blue,
          child: const Icon(Icons.add),
        ),
        backgroundColor: const Color.fromARGB(1000, 191, 218, 208),
        body: Padding(
          padding: const EdgeInsets.all(20.0),
          child: BlocConsumer<MyOrdersBloc, MyOrdersState>(
            buildWhen: (context, state) {
              return state is MyOrdersInitial ||
                  state is MyOrdersSuccess ||
                  state is MyOrdersError ||
                  state is MyOrdersLoading;
            },
            builder: (context, state) {
              if (state is MyOrdersSuccess) {
                return Center(child: _buildHomeWidget(state.userResponse));
              } else if (state is MyOrdersError) {
                return const Text('error');
              } else if (state is MyOrdersLoading) {
                return const Center(child: CircularProgressIndicator());
              }
              return const Center(child: Text('data'));
            },
            listener: (BuildContext context, MyOrdersState state) {},
          ),
        ),
      ),
    );
  }

  _buildHomeWidget(UserResponse me) {
    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Container(
                width: 50,
                height: 50,
                decoration: BoxDecoration(
                  shape: BoxShape.circle,
                  border: Border.all(color: Colors.black),
                ),
                child: ClipRRect(
                  borderRadius: BorderRadius.circular(25),
                  child: Image.network(
                    me.profilePicture,
                    fit: BoxFit.cover,
                  ),
                ),
              ),
              const Icon(Icons.notifications)
            ],
          ),
        ),
        Container(
            margin: const EdgeInsets.all(12),
            padding: const EdgeInsetsDirectional.fromSTEB(0, 0, 210, 0),
            child: const Text('Your posts')),
        _orderListWidget()
      ],
    );
  }

  _orderListWidget() {
    return const Expanded(
        child: OrderListWidget(title: 'searchTitle', listType: 1));
  }
}
