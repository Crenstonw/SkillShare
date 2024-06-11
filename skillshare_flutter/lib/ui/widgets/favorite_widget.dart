import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:skillshare_flutter/blocs/orderFavorite/order_favorite_bloc.dart';
import 'package:skillshare_flutter/models/responses/favorite_orders_response.dart';
import 'package:skillshare_flutter/models/responses/user_response.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository_impl.dart';
import 'package:skillshare_flutter/repositories/user/user_repository.dart';
import 'package:skillshare_flutter/repositories/user/user_repository_impl.dart';
import 'package:skillshare_flutter/ui/order_detail_page.dart';

class FavoriteWidget extends StatefulWidget {
  const FavoriteWidget({super.key});

  @override
  State<FavoriteWidget> createState() => _FavoriteWidgetState();
}

class _FavoriteWidgetState extends State<FavoriteWidget> {
  late OrderListRepository orderListRepository;
  late UserRepository userRepository;
  late OrderFavoriteBloc _orderFavoriteBloc;

  @override
  void initState() {
    orderListRepository = OrderListRepositoryImpl();
    userRepository = UserRepositoryImpl();
    _orderFavoriteBloc = OrderFavoriteBloc(orderListRepository, userRepository)
      ..add(DoOrderFavoriteEvent());
    super.initState();
  }

  void reload() {
    orderListRepository = OrderListRepositoryImpl();
    _orderFavoriteBloc = OrderFavoriteBloc(orderListRepository, userRepository)
      ..add(DoOrderFavoriteEvent());
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider.value(
      value: _orderFavoriteBloc,
      child: Scaffold(
        backgroundColor: const Color.fromARGB(1000, 191, 218, 208),
        body: Padding(
          padding: const EdgeInsets.all(20.0),
          child: BlocConsumer<OrderFavoriteBloc, OrderFavoriteState>(
            buildWhen: (context, state) {
              return state is OrderFavoriteInitial ||
                  state is OrderFavoriteSuccess ||
                  state is OrderFavoriteError ||
                  state is OrderFavoriteLoading;
            },
            builder: (context, state) {
              if (state is OrderFavoriteSuccess) {
                return Center(
                    child: _buildFavoriteWidget(state.favorites, state.me));
              } else if (state is OrderFavoriteError) {
                return const Text('error');
              } else if (state is OrderFavoriteLoading) {
                return const Center(child: CircularProgressIndicator());
              }
              return const Center(child: Text('data'));
            },
            listener: (BuildContext context, OrderFavoriteState state) {},
          ),
        ),
      ),
    );
  }
}

_buildFavoriteWidget(List<FavoriteOrdersResponse> orders, UserResponse me) {
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
          //padding: const EdgeInsetsDirectional.fromSTEB(0, 0, 210, 0),
          child: const Row(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              Text('Your favorites posts'),
            ],
          )),
          _orderList(orders)
    ],
  );
}

_orderList(List<FavoriteOrdersResponse> orders) {
  if(orders.isEmpty) {
    return const Text('You don\'t have any favorites yet :(');
  } else {
  return Expanded(
    child: ListView.builder(
      itemCount: orders.length,
      itemBuilder: (context, index) {
        return Padding(
          padding: const EdgeInsets.fromLTRB(2, 10, 2, 10),
          child: InkWell(
              onTap: () {
                FavoriteOrdersResponse order = orders[index];
                Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) =>
                            OrderDetailPage(orderId: order.id)));
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
                                    orders[index].user.profilePicture,
                                    fit: BoxFit.cover,
                                  ),
                                ),
                              ),
                              SizedBox(
                                width: 180,
                                child: Padding(
                                  padding: const EdgeInsets.all(8.0),
                                  child: Text(
                                    orders[index].user.username,
                                    overflow: TextOverflow.ellipsis,
                                  ),
                                ),
                              ),
                            ],
                          ),
                          Row(
                            children: [
                              if (orders[index].state == 'OPEN')
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
                              if (orders[index].state == 'OCCUPIED')
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
                              if (orders[index].state == 'CLOSED')
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
                    Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              orders[index].title,
                              style: const TextStyle(fontSize: 20),
                            ),
                            Text('${orders[index].price}\$')
                          ],
                        ))
                  ],
                ),
              )),
        );
      },
    ),
  );
  }
}
