import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:skillshare_flutter/blocs/orderList/order_list_bloc.dart';
import 'package:skillshare_flutter/models/order_list_response.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository_impl.dart';

class OrderListWidget extends StatefulWidget {
  const OrderListWidget({super.key});

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
      ..add(DoOrderListEvent());
    super.initState();
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
        return Card(
          child: Column(
            children: [
              Text(orderList[index].title!,
                  style: const TextStyle(fontSize: 24)),
            ],
          ),
        );
      },
    );
  }
}
