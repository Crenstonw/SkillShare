import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:skillshare_flutter/blocs/orderDetail/order_detail_bloc.dart';
import 'package:skillshare_flutter/models/responses/order_detail_response.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository_impl.dart';
import 'package:skillshare_flutter/repositories/user/user_repository.dart';
import 'package:skillshare_flutter/repositories/user/user_repository_impl.dart';
import 'package:skillshare_flutter/ui/widgets/new_comment.dart';

class OrderDetailPage extends StatefulWidget {
  final String orderId;
  const OrderDetailPage({super.key, required this.orderId});

  @override
  State<OrderDetailPage> createState() => _OrderDetailPageState();
}

class _OrderDetailPageState extends State<OrderDetailPage> {
  late OrderListRepository orderListRepository;
  late UserRepository userRepository;
  late OrderDetailBloc _orderDetailBloc;

  _addFavorite() {
    
  }

  String date(DateTime dateTime, bool justDate) {
    List<String> date = dateTime.toString().split(' ')[0].split('-');
    List<String> time = dateTime.toString().split(' ')[1].split(':');
    print(dateTime);
    num response;
    if(justDate) {
      return dateTime.toString().split(' ')[0];
    }
    if (date[0] != DateTime.now().year.toString()) {
      response = DateTime.now().year - int.parse(date[0]);
      return '${response} year${response != 1 ? 's' : ''} ago';
    } else if ((int.parse(date[1])) != DateTime.now().month) {
      response = DateTime.now().month - (int.parse(date[1]));
      return '${response} month${response != 1 ? 's' : ''} ago';
    } else if (int.parse(date[2]) != DateTime.now().day) {
      response = DateTime.now().day - int.parse(date[2]);
      return '${response} day${response != 1 ? 's' : ''} ago';
    } else if (int.parse(time[0]) != (DateTime.now().hour+2)) {
      response = (DateTime.now().hour+2) - int.parse(time[0]);
      return '${response} hour${response != 1 ? 's' : ''} ago';
    } else if (int.parse(time[1]) != DateTime.now().minute) {
      response = DateTime.now().minute - int.parse(time[1]);
      return '${response} minute${response != 1 ? 's' : ''} ago';
    } else {
      return 'Just now';
    }
  }

   void _showModal(BuildContext context) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return NewComment(orderId: widget.orderId);
      },
    );
  }

  @override
  void initState() {
    orderListRepository = OrderListRepositoryImpl();
    userRepository = UserRepositoryImpl();
    _orderDetailBloc = OrderDetailBloc(orderListRepository, userRepository)
      ..add(DoOrderDetailEvent(widget.orderId));
    super.initState();
  }

  void reload() {
    orderListRepository = OrderListRepositoryImpl();
    _orderDetailBloc = OrderDetailBloc(orderListRepository, userRepository)
      ..add(DoOrderDetailEvent(widget.orderId));
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider.value(
      value: _orderDetailBloc,
      child: BlocBuilder<OrderDetailBloc, OrderDetailState>(
        builder: (context, state) {
          if (state is OrderDetailsSuccess) {
            if(state.me.id != state.orderDetailResponse.user.id) {
              return Center(
                child: _buildOrderDetailWidget(state.orderDetailResponse));
            } else {
              return Center(
                child: _buildOrderDetailWidgetAdministrable(state.orderDetailResponse));
            }
          } else if (state is DoOrderListError) {
            return const Text('error');
          } else if (state is OrderDetailsLoading) {
            return const Center(child: CircularProgressIndicator());
          }
          return const Text('data');
        },
      ),
    );
  }

  _buildOrderDetailWidget(OrderDetailResponse order) {
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
              Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Flexible(
                    child: Text(
                      order.title,
                      style: const TextStyle(fontSize: 20),
                    ),
                  ),
                  const SizedBox(width: 10),
                  if (order.state == 'OPEN')
                    Container(
                      margin: const EdgeInsets.symmetric(horizontal: 4.0),
                      decoration: const BoxDecoration(
                        color: Colors.green,
                        borderRadius: BorderRadius.all(Radius.circular(25)),
                      ),
                      child: const Padding(
                        padding: EdgeInsets.all(8.0),
                        child: Text(
                          'Open',
                          style: TextStyle(color: Colors.white),
                        ),
                      ),
                    ),
                  if (order.state == 'OCCUPIED')
                    Container(
                      margin: const EdgeInsets.symmetric(horizontal: 4.0),
                      decoration: const BoxDecoration(
                        color: Colors.yellow,
                        borderRadius: BorderRadius.all(Radius.circular(25)),
                      ),
                      child: const Padding(
                        padding: EdgeInsets.all(8.0),
                        child: Text('Occupied'),
                      ),
                    ),
                  if (order.state == 'CLOSED')
                    Container(
                      margin: const EdgeInsets.symmetric(horizontal: 4.0),
                      decoration: const BoxDecoration(
                        color: Colors.red,
                        borderRadius: BorderRadius.all(Radius.circular(25)),
                      ),
                      child: const Padding(
                        padding: EdgeInsets.all(8.0),
                        child: Text(
                          'Closed',
                          style: TextStyle(color: Colors.white),
                        ),
                      ),
                    ),
                ],
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Row(
                    children: [
                      Container(
                        width: 50,
                        height: 50,
                        decoration: BoxDecoration(
                          shape: BoxShape.circle,
                          border: Border.all(color: Colors.black),
                        ),
                        child: ClipRRect(
                          borderRadius: BorderRadius.circular(50),
                          child: Image.network(
                            order.user.profilePicture,
                            fit: BoxFit.cover,
                          ),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.fromLTRB(8.0, 0, 0, 0),
                        child: Text(order.user.username),
                      )
                    ],
                  ),
                  Text(
                    '\$${order.price}',
                    style: const TextStyle(fontSize: 30),
                  ),
                ],
              ),
              /*TextButton(onPressed: () {
                _addFavorite();
              }, child: 
              const Text('Add Favorite'))*/
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
                Text(order.description)
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
                if (order.tags.isNotEmpty)
                  SingleChildScrollView(
                    scrollDirection: Axis.horizontal,
                    child: Row(
                      children: [
                        for (int i = 0; i < order.tags.length; i++)
                          Container(
                            margin: const EdgeInsets.symmetric(horizontal: 4.0),
                            decoration: const BoxDecoration(
                              color: Colors.black,
                              borderRadius:
                                  BorderRadius.all(Radius.circular(25)),
                            ),
                            child: Padding(
                              padding: const EdgeInsets.all(8.0),
                              child: Text(order.tags[i].name,
                                  style: const TextStyle(color: Colors.white)),
                            ),
                          ),
                      ],
                    ),
                  )
                else
                  const Text('No tags available')
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
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                     const Text('COMMENTS', style: TextStyle(fontSize: 20)),
                     ElevatedButton(onPressed: () {
                      _showModal(context);
                     },child: const Text('Leave a comment'))
                  ],
                ),
                if (order.messages.isEmpty)
                  const Text('No comments')
                else
                  for (int i = 0; i < order.messages.length; i++)
                    Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Container(
                        decoration: const BoxDecoration(
                          border: Border(
                            bottom: BorderSide(
                                width: 1.0,
                                color: Colors
                                    .grey),
                          ),
                        ),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Text(order.messages[i].author.username),
                                Text(date(order.messages[i].dateTime, false)),
                              ],
                            ),
                            Text(
                              order.messages[i].title,
                              style: const TextStyle(fontSize: 20),
                            ),
                            Text(order.messages[i].message),
                          ],
                        ),
                      ),
                    )
              ],
            ),
          ),
        )
      ]),
    );
  }

  _buildOrderDetailWidgetAdministrable(OrderDetailResponse order) {
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
              Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Flexible(
                    child: Text(
                      order.title,
                      style: const TextStyle(fontSize: 20),
                    ),
                  ),
                  const SizedBox(width: 10),
                  if (order.state == 'OPEN')
                    Container(
                      margin: const EdgeInsets.symmetric(horizontal: 4.0),
                      decoration: const BoxDecoration(
                        color: Colors.green,
                        borderRadius: BorderRadius.all(Radius.circular(25)),
                      ),
                      child: const Padding(
                        padding: EdgeInsets.all(8.0),
                        child: Text(
                          'Open',
                          style: TextStyle(color: Colors.white),
                        ),
                      ),
                    ),
                  if (order.state == 'OCCUPIED')
                    Container(
                      margin: const EdgeInsets.symmetric(horizontal: 4.0),
                      decoration: const BoxDecoration(
                        color: Colors.yellow,
                        borderRadius: BorderRadius.all(Radius.circular(25)),
                      ),
                      child: const Padding(
                        padding: EdgeInsets.all(8.0),
                        child: Text('Occupied'),
                      ),
                    ),
                  if (order.state == 'CLOSED')
                    Container(
                      margin: const EdgeInsets.symmetric(horizontal: 4.0),
                      decoration: const BoxDecoration(
                        color: Colors.red,
                        borderRadius: BorderRadius.all(Radius.circular(25)),
                      ),
                      child: const Padding(
                        padding: EdgeInsets.all(8.0),
                        child: Text(
                          'Closed',
                          style: TextStyle(color: Colors.white),
                        ),
                      ),
                    ),
                ],
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Row(
                    children: [
                      Container(
                        width: 50,
                        height: 50,
                        decoration: BoxDecoration(
                          shape: BoxShape.circle,
                          border: Border.all(color: Colors.black),
                        ),
                        child: ClipRRect(
                          borderRadius: BorderRadius.circular(50),
                          child: Image.network(
                            order.user.profilePicture,
                            fit: BoxFit.cover,
                          ),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.fromLTRB(8.0, 0, 0, 0),
                        child: Text(order.user.username),
                      )
                    ],
                  ),
                  Text(
                    '\$${order.price}',
                    style: const TextStyle(fontSize: 30),
                  ),
                ],
              ),
              /*TextButton(onPressed: () {
                _addFavorite();
              }, child: 
              const Text('Add Favorite'))*/
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
                Text(order.description)
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
                if (order.tags.isNotEmpty)
                  SingleChildScrollView(
                    scrollDirection: Axis.horizontal,
                    child: Row(
                      children: [
                        for (int i = 0; i < order.tags.length; i++)
                          Container(
                            margin: const EdgeInsets.symmetric(horizontal: 4.0),
                            decoration: const BoxDecoration(
                              color: Colors.black,
                              borderRadius:
                                  BorderRadius.all(Radius.circular(25)),
                            ),
                            child: Padding(
                              padding: const EdgeInsets.all(8.0),
                              child: Text(order.tags[i].name,
                                  style: const TextStyle(color: Colors.white)),
                            ),
                          ),
                      ],
                    ),
                  )
                else
                  const Text('No tags available')
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
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                     const Text('COMMENTS', style: TextStyle(fontSize: 20)),
                     ElevatedButton(onPressed: () {
                      _showModal(context);
                     },child: const Text('Leave a comment'))
                  ],
                ),
                if (order.messages.isEmpty)
                  const Text('No comments')
                else
                  for (int i = 0; i < order.messages.length; i++)
                    Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Container(
                        decoration: const BoxDecoration(
                          border: Border(
                            bottom: BorderSide(
                                width: 1.0,
                                color: Colors
                                    .grey),
                          ),
                        ),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Text(order.messages[i].author.username),
                                Text(date(order.messages[i].dateTime, false)),
                              ],
                            ),
                            Text(
                              order.messages[i].title,
                              style: const TextStyle(fontSize: 20),
                            ),
                            Text(order.messages[i].message),
                          ],
                        ),
                      ),
                    )
              ],
            ),
          ),
        )
      ]),
    );
  }
}
