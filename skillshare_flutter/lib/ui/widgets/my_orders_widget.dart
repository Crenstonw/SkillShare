import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:skillshare_flutter/blocs/myOrders/my_orders_bloc.dart';
import 'package:skillshare_flutter/models/responses/user_response.dart';
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
  late OrderListRepository orderListRespository;
  late UserRepository userRepository;
  late MyOrdersBloc _myOrdersBloc;

   @override
  void initState() {
    orderListRespository = OrderListRepositoryImpl();
    userRepository = UserRepositoryImpl();
    _myOrdersBloc = MyOrdersBloc(orderListRespository);
    _myOrdersBloc.add(DoMyOrdersEvent());
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider.value(
      value: _myOrdersBloc,
      child: Scaffold(
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
                return Center(child: _buildHomeWidget());
              } else if (state is MyOrdersError) {
                return Center(
                    child: Column(
                  children: [
                    const Text(
                      'An error ocurred, page didn\'t load correctly',
                      style: TextStyle(color: Colors.red),
                    ),
                    _buildHomeWidget()
                  ],
                ));
              } else if (state is MyOrdersLoading) {
                return const Center(child: CircularProgressIndicator());
              }
              return Center(child: _buildHomeWidget());
            },
            listener: (BuildContext context, MyOrdersState state) {},
          ),
        ),
      ),
    );
  }

  _buildHomeWidget() {
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
                    'https://imgs.search.brave.com/UtmBRE2uMVKCGvYr-Ufs4f8GMP99aA1DFMJhnfoSefM/rs:fit:860:0:0/g:ce/aHR0cHM6Ly9lMDAt/ZWxtdW5kby51ZWNk/bi5lcy9hc3NldHMv/bXVsdGltZWRpYS9p/bWFnZW5lcy8yMDIz/LzA0LzEyLzE2ODEz/MTE1NTQwMDA1Lmpw/Zw',
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
    return const Expanded(child: OrderListWidget(title: 'searchTitle', listType: 1,));
  }
}