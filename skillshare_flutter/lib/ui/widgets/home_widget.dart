import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:skillshare_flutter/blocs/home/home_bloc.dart';
import 'package:skillshare_flutter/repositories/home/home_repository.dart';
import 'package:skillshare_flutter/repositories/home/home_repository_impl.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository_impl.dart';
import 'package:skillshare_flutter/ui/widgets/order_list_widget.dart';

class HomeWidget extends StatefulWidget {
  const HomeWidget({super.key});

  @override
  State<HomeWidget> createState() => _HomeWidgetState();
}

class _HomeWidgetState extends State<HomeWidget> {
  final userTextController = TextEditingController();
  final passTextController = TextEditingController();

  late HomeRepository homeRepository;
  late HomeBloc _homeBloc;

  late String profilePicture =
      "https://as2.ftcdn.net/v2/jpg/03/49/49/79/1000_F_349497933_Ly4im8BDmHLaLzgyKg2f2yZOvJjBtlw5.jpg";

  late String searchTitle = '';

  @override
  void initState() {
    homeRepository = HomeRepositoryImpl();
    _homeBloc = HomeBloc(homeRepository);
    _homeBloc.add(DoHomeEvent());
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
      value: _homeBloc,
      child: Scaffold(
        backgroundColor: const Color.fromARGB(1000, 191, 218, 208),
        body: Padding(
          padding: const EdgeInsets.all(20.0),
          child: BlocConsumer<HomeBloc, HomeState>(
            buildWhen: (context, state) {
              return state is HomeInitial ||
                  state is DoHomeSuccess ||
                  state is DoHomeError ||
                  state is DoHomeLoading;
            },
            builder: (context, state) {
              if (state is DoHomeSuccess) {
                return Center(child: _buildHomeWidget(state.userResponse));
              } else if (state is DoHomeError) {
                return Center(
                    child: Column(
                  children: [
                    const Text(
                      'An error ocurred, page didn\'t load correctly',
                      style: TextStyle(color: Colors.red),
                    ),
                    _buildHomeWidget(widget.toString())
                  ],
                ));
              } else if (state is DoHomeLoading) {
                return const Center(child: CircularProgressIndicator());
              }
              return Center(child: _buildHomeWidget(profilePicture));
            },
            /*listenWhen: (context, state) {
              return state is GetRequestTokenSuccess;
            },*/
            listener: (BuildContext context, HomeState state) {},
          ),
        ),
      ),
    );
  }

  _buildHomeWidget(String pf) {
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
                    pf,
                    fit: BoxFit.cover,
                  ),
                ),
              ),
              const Icon(Icons.notifications)
            ],
          ),
        ),
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: Container(
            decoration: BoxDecoration(
              color: const Color.fromARGB(255, 19, 91, 70),
              borderRadius: BorderRadius.circular(30),
            ),
            padding: const EdgeInsets.all(0.1),
            child: Row(
              children: [
                Expanded(
                  child: TextField(
                    decoration: const InputDecoration(
                      hintText: 'Search...',
                      filled: true,
                      fillColor: Color.fromARGB(255, 19, 91, 70),
                    ),
                    onChanged: (value) {
                      setState(() {
                        searchTitle = value;
                      });
                    },
                  ),
                ),
                IconButton(
                  icon: const Icon(
                    Icons.search,
                    color: Colors.white,
                  ),
                  onPressed: () {},
                ),
              ],
            ),
          ),
        ),
        Container(
            margin: const EdgeInsets.all(12),
            padding: const EdgeInsetsDirectional.fromSTEB(0, 0, 210, 0),
            child: const Text('Jobs relevant to you')),
        Expanded(child: OrderListWidget(title: searchTitle))
      ],
    );
  }
}
