import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:skillshare_flutter/blocs/orderList/order_list_bloc.dart';
import 'package:skillshare_flutter/models/order_list_response.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository_impl.dart';
import 'package:skillshare_flutter/ui/order_detail_page.dart';

class OrderListWidget extends StatefulWidget {
  final String title;
  const OrderListWidget({super.key, required this.title});

  @override
  State<OrderListWidget> createState() => _OrderListWidgetState();
}

class _OrderListWidgetState extends State<OrderListWidget> {
  late OrderListRepository orderListRepository;
  late OrderListBloc _orderListBloc;

  @override
  void initState() {
    orderListRepository = OrderListRepositoryImpl();
    _orderListBloc = OrderListBloc(orderListRepository)
      ..add(DoOrderListEvent(widget.title));
    super.initState();
  }

  void reload() {
    orderListRepository = OrderListRepositoryImpl();
    _orderListBloc = OrderListBloc(orderListRepository)
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

  _buildOrderListWidget(List<Order> orderList) {
    return ListView.builder(
      itemCount: orderList.length,
      itemBuilder: (context, index) {
        return Padding(
          padding: const EdgeInsets.fromLTRB(2, 10, 2, 10),
          child: InkWell(
            onTap: () {
              Order order = orderList[index];
              Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => OrderDetailPage(order: order)));
            },
            child: Card(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
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
                                  orderList[index].user.profilePicture,
                                  fit: BoxFit.cover,
                                ),
                              ),
                            ),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                SizedBox(
                                  width: 220,
                                  child: Padding(
                                    padding: const EdgeInsets.all(8.0),
                                    child: Text(
                                      orderList[index].title,
                                      style: const TextStyle(fontSize: 20),
                                    ),
                                  ),
                                ),
                                Padding(
                                  padding:
                                      const EdgeInsets.fromLTRB(8, 0, 0, 0),
                                  child: Text(
                                      'By: ${orderList[index].user.username}'),
                                )
                              ],
                            ),
                          ],
                        ),
                        const Padding(
                          padding: EdgeInsets.all(8.0),
                          child: Icon(
                            Icons.favorite_border,
                            color: Colors.red,
                            size: 35,
                          ),
                        )
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ),
        );
      },
    );
  }
}
