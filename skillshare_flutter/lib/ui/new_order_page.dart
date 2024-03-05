import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:skillshare_flutter/blocs/newOrder/new_order_bloc.dart';
import 'package:skillshare_flutter/repositories/newOrder/new_order_repository.dart';
import 'package:skillshare_flutter/repositories/newOrder/new_order_repository_impl.dart';

class NewOrderPage extends StatefulWidget {
  const NewOrderPage({super.key});

  @override
  State<NewOrderPage> createState() => _NewOrderPageState();
}

class _NewOrderPageState extends State<NewOrderPage> {
  final userTextController = TextEditingController();
  final passTextController = TextEditingController();

  late String title;
  late String description;

  late NewOrderRepository newOrderRepository;
  late NewOrderBloc _newOrderBloc;

  @override
  void initState() {
    newOrderRepository = NewOrderRepositoryImpl();
    _newOrderBloc = NewOrderBloc(newOrderRepository);
    super.initState();
  }

  @override
  void dispose() {
    userTextController.dispose();
    passTextController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider.value(
      value: _newOrderBloc,
      child: Scaffold(
        backgroundColor: const Color.fromARGB(1000, 191, 218, 208),
        body: Padding(
          padding: const EdgeInsets.all(20.0),
          child: BlocConsumer<NewOrderBloc, NewOrderState>(
            buildWhen: (context, state) {
              return state is NewOrderInitial ||
                  state is DoNewOrderSuccess ||
                  state is DoNewOrderError ||
                  state is DoNewOrderLoading;
            },
            builder: (context, state) {
              if (state is DoNewOrderSuccess) {
                return const Text('success');
              } else if (state is DoNewOrderError) {
                return const Text('error');
              } else if (state is DoNewOrderLoading) {
                return const Center(child: CircularProgressIndicator());
              }
              return Center(child: _buildNewOrderForm());
            },
            listener: (BuildContext context, NewOrderState state) {},
          ),
        ),
      ),
    );
  }

  _buildNewOrderForm() {
    return Padding(
      padding: const EdgeInsets.only(top: 70),
      child: SizedBox(
        height: 2000,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
            const Text(
              'SkillShare',
              style: TextStyle(
                  fontSize: 38,
                  color: Colors.white,
                  fontWeight: FontWeight.w900),
            ),
            const Text(
              'New Order',
              style: TextStyle(fontSize: 26),
            ),
            Card(
              child: Padding(
                padding: const EdgeInsets.all(16),
                child: TextFormField(
                  decoration: const InputDecoration(
                    labelText: 'title',
                  ),
                  onChanged: (value) => title = value,
                ),
              ),
            ),
            Card(
              child: Padding(
                padding: const EdgeInsets.all(16),
                child: TextFormField(
                  decoration: const InputDecoration(
                    labelText: 'description',
                  ),
                  onChanged: (value) => description = value,
                ),
              ),
            ),
            ElevatedButton(
              onPressed: () {
                _newOrderBloc.add(DoNewOrderEvent(title, description));
              },
              child: const Text(
                'Create a new order',
                style: TextStyle(
                  fontSize: 18,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
