import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:skillshare_flutter/blocs/login/login_bloc.dart';
import 'package:skillshare_flutter/repositories/login/login_repository.dart';
import 'package:skillshare_flutter/repositories/login/login_repository_impl.dart';
import 'package:shared_preferences/shared_preferences.dart';
//https://www.youtube.com/watch?v=GGNiT9PgrFQ

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final _formLogin = GlobalKey<FormState>();
  final userTextController = TextEditingController();
  final passTextController = TextEditingController();

  late LoginRepository loginRepository;
  late LoginBloc _loginBloc;

  @override
  void initState() {
    loginRepository = LoginRepositoryImpl();
    _loginBloc = LoginBloc(loginRepository);
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
      value: _loginBloc,
      child: Scaffold(
        body: Padding(
          padding: const EdgeInsets.all(20.0),
          child: BlocConsumer<LoginBloc, LoginState>(
            buildWhen: (context, state) {
              return state is LoginInitial ||
                  state is DoLoginSuccess ||
                  state is DoLoginError ||
                  state is DoLoginLoading;
            },
            builder: (context, state) {
              if (state is DoLoginSuccess) {
                return const Text('SharedPreferences.getString().toString()');
              } else if (state is DoLoginError) {
                return const Text('Login error');
              } else if (state is DoLoginLoading) {
                return const Center(child: CircularProgressIndicator());
              }
              return Center(child: _buildLoginForm());
            },
            /*listenWhen: (context, state) {
              return state is GetRequestTokenSuccess;
            },*/
            listener: (BuildContext context, LoginState state) {},
          ),
        ),
      ),
    );
  }

  _buildLoginForm() {
    return Form(
      key: _formLogin,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text(
            'Login',
            textAlign: TextAlign.start,
            style: TextStyle(fontSize: 40),
          ),
          const SizedBox(
            height: 20,
          ),
          TextFormField(
            controller: userTextController,
            decoration: const InputDecoration(
                border: OutlineInputBorder(), labelText: 'Email'),
            validator: (value) {
              if (value == null || value.isEmpty) {
                return 'Please enter some text';
              }
              return null;
            },
          ),
          const SizedBox(
            height: 20,
          ),
          TextFormField(
            controller: passTextController,
            obscureText: true,
            decoration: const InputDecoration(
                border: OutlineInputBorder(), labelText: 'Password'),
            validator: (value) {
              if (value == null || value.isEmpty) {
                return 'Please enter some text';
              }
              return null;
            },
          ),
          const SizedBox(
            height: 20,
          ),
          SizedBox(
            width: double.infinity,
            child: ElevatedButton(
              child: Text('Login'.toUpperCase()),
              onPressed: () {
                if (_formLogin.currentState!.validate()) {
                  _loginBloc.add(DoLoginEvent(
                      userTextController.text, passTextController.text));
                }
              },
            ),
          ),
        ],
      ),
    );
  }
}
