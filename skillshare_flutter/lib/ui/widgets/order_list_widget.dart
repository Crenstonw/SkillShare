import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:skillshare_flutter/blocs/orderList/order_list_bloc.dart';
import 'package:skillshare_flutter/models/responses/all_order_response.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository_impl.dart';
import 'package:skillshare_flutter/ui/order_detail_page.dart';

class OrderListWidget extends StatefulWidget {
  final String title;
  final int listType;
  const OrderListWidget({super.key, required this.title, required this.listType});

  @override
  State<OrderListWidget> createState() => _OrderListWidgetState();
}

class _OrderListWidgetState extends State<OrderListWidget> {
  late OrderListRepository orderListRepository;
  late OrderListBloc _orderListBloc;

  String date(DateTime dateTime, bool justDate) {
    List<String> date = dateTime.toString().split(' ')[0].split('-');
    List<String> time = dateTime.toString().split(' ')[1].split(':');
    time[2] = time[2].split('.')[0];
    num response;
    if (justDate) {
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
    } else if (int.parse(time[0]) != (DateTime.now().hour + 2)) {
      response = (DateTime.now().hour + 2) - int.parse(time[0]);
      return '${response} hour${response != 1 ? 's' : ''} ago';
    } else if (int.parse(time[1]) != DateTime.now().minute) {
      response = DateTime.now().minute - int.parse(time[1]);
      return '${response} minute${response != 1 ? 's' : ''} ago';
    } else {
      return 'Just now';
    }
  }

  @override
  void initState() {
    orderListRepository = OrderListRepositoryImpl();
    _orderListBloc = OrderListBloc(orderListRepository, widget.listType)
      ..add(DoOrderListEvent(widget.title));
    super.initState();
  }

  void reload() {
    orderListRepository = OrderListRepositoryImpl();
    _orderListBloc = OrderListBloc(orderListRepository, widget.listType)
      ..add(DoOrderListEvent(widget.title));
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider.value(
      value: _orderListBloc,
      child: BlocBuilder<OrderListBloc, OrderListState>(
        builder: (context, state) {
          if (state is DoOrderListSuccess) {
            return Center(
                child: _buildOrderListWidget(state.orderListResponse));
          } else if (state is DoOrderListError) {
            return const Text('error');
          } else if (state is DoOrderListLoading) {
            return const Center(child: CircularProgressIndicator());
          }
          return const Text('data');
        },
      ),
    );
  }

  _buildOrderListWidget(AllOrderResponse orderList) {
    return ListView.builder(
      itemCount: orderList.content.length,
      itemBuilder: (context, index) {
        return Padding(
          padding: const EdgeInsets.fromLTRB(2, 10, 2, 10),
          child: InkWell(
              onTap: () {
                Content order = orderList.content[index];
                Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => OrderDetailPage(orderId: order.id)));
              },
              child: Card(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Padding(
                      padding: const EdgeInsets.fromLTRB(8, 8, 0, 0),
                      child: Text('Created: ${date(orderList.content[index].createdAt, false)}'),
                    ),
                    Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Row(
                            children: [
                              Container(
                                width: 45,
                                height: 45,
                                decoration: BoxDecoration(
                                  shape: BoxShape.circle,
                                  border: Border.all(color: Colors.black),
                                ),
                                child: ClipRRect(
                                  borderRadius: BorderRadius.circular(25),
                                  child: Image.network(
                                    orderList
                                        .content[index].user.profilePicture,
                                    fit: BoxFit.cover,
                                  ),
                                ),
                              ),
                              SizedBox(
                                width: 180,
                                child: Padding(
                                  padding: const EdgeInsets.all(8.0),
                                  child: Text(
                                    orderList.content[index].user.username,
                                    overflow: TextOverflow
                                        .ellipsis,
                                  ),
                                ),
                              ),
                            ],
                          ),
                          Row(
                            children: [
                              if (orderList.content[index].state == 'OPEN')
                                Container(
                                  margin: const EdgeInsets.symmetric(
                                      horizontal: 4.0),
                                  decoration: const BoxDecoration(
                                    color: Colors.green,
                                    borderRadius:
                                        BorderRadius.all(Radius.circular(25)),
                                  ),
                                  child: const Padding(
                                    padding: EdgeInsets.all(8.0),
                                    child: Text('Open',
                                        style: TextStyle(color: Colors.white)),
                                  ),
                                ),
                              if (orderList.content[index].state == 'OCCUPIED')
                                Container(
                                  margin: const EdgeInsets.symmetric(
                                      horizontal: 4.0),
                                  decoration: const BoxDecoration(
                                    color: Colors.yellow,
                                    borderRadius:
                                        BorderRadius.all(Radius.circular(25)),
                                  ),
                                  child: const Padding(
                                    padding: EdgeInsets.all(8.0),
                                    child: Text('Occupied'),
                                  ),
                                ),
                              if (orderList.content[index].state == 'CLOSED')
                                Container(
                                  margin: const EdgeInsets.symmetric(
                                      horizontal: 4.0),
                                  decoration: const BoxDecoration(
                                    color: Colors.red,
                                    borderRadius:
                                        BorderRadius.all(Radius.circular(25)),
                                  ),
                                  child: const Padding(
                                    padding: EdgeInsets.all(8.0),
                                    child: Text('Closed',
                                    style: TextStyle(color: Colors.white)),
                                  ),
                                ),
                            ],
                          ),
                        ],
                      ),
                    ),
                    Padding(padding: const EdgeInsets.all(8.0), 
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text(orderList.content[index].title, style: const TextStyle(fontSize: 20),),
                        Text('${orderList.content[index].price}\$')
                      ],
                    ))
                  ],
                ),
              )),
        );
      },
    );
  }
}
