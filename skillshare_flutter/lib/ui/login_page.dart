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
  //final _formLogin = GlobalKey<FormState>();
  final userTextController = TextEditingController();
  final passTextController = TextEditingController();

  late String email;
  late String password;

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
        backgroundColor: const Color.fromARGB(1000, 191, 218, 208),
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
                return const Text('Login success');
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
    return /*Form(
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
    );*/
       Padding(
        padding: EdgeInsets.only(top: 70),
        child: SizedBox(
          height: 2000,
          child: Column(
            children: [
              const Text(
                'SkillShare',
                style: TextStyle(
                    fontSize: 38,
                    color: Colors.white,
                    fontWeight: FontWeight.w900),
              ),
              const Text(
                'Let\'s get started',
                style: TextStyle(fontSize: 26),
              ),
              Card(
                child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: TextFormField(
                    decoration: const InputDecoration(
                      labelText: 'email',
                    ),
                    onChanged: (value) => email = value,
                  ),
                ),
              ),
              Card(
                child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: TextFormField(
                    decoration: const InputDecoration(
                      labelText: 'password',
                    ),
                    onChanged: (value) => password = value,
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(10),
                child: RichText(
                  text: const TextSpan(children: [
                    TextSpan(
                        text: 'Don\'t you have an account? ',
                        style: TextStyle(color: Colors.black)),
                    TextSpan(
                        text: 'Sign up',
                        style: TextStyle(
                            color: Color.fromARGB(1000, 18, 170, 115)))
                  ]),
                ),
              ),
              ElevatedButton(
                onPressed: () {
                    _loginBloc.add(DoLoginEvent(email, password));
                },
                child: const Text(
                  'LogIn',
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
