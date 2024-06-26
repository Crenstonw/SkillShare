import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:skillshare_flutter/blocs/login/login_bloc.dart';
import 'package:skillshare_flutter/repositories/login/login_repository.dart';
import 'package:skillshare_flutter/repositories/login/login_repository_impl.dart';
import 'package:skillshare_flutter/ui/home_page.dart';
import 'package:skillshare_flutter/ui/register_page.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
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
                WidgetsBinding.instance.addPostFrameCallback((_) {
                  Navigator.pushAndRemoveUntil(
                    (context),
                    MaterialPageRoute(
                      builder: (BuildContext context) => const HomePage(),
                    ),
                    (route) => false,
                  );
                });
              } else if (state is DoLoginError) {
                return _buildLoginForm(true);
              } else if (state is DoLoginLoading) {
                return const Center(child: CircularProgressIndicator());
              }
              return Center(child: _buildLoginForm(false));
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

  _buildLoginForm(bool isLoginError) {
    if (isLoginError) {
      return Padding(
        padding: const EdgeInsets.only(top: 70),
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
                    obscureText: true,
                  ),
                ),
              ),
              const Text('Incorrect credentials',
                  style: TextStyle(color: Colors.red)),
              Padding(
                padding: const EdgeInsets.all(10),
                child: RichText(
                  text: TextSpan(children: [
                    const TextSpan(
                        text: 'Don\'t you have an account? ',
                        style: TextStyle(color: Colors.black)),
                    TextSpan(
                        text: 'Sign up',
                        style: const TextStyle(
                            color: Color.fromARGB(1000, 18, 170, 115)),
                        recognizer: TapGestureRecognizer()
                          ..onTap = () {
                            Navigator.pushAndRemoveUntil(
                              context,
                              MaterialPageRoute(
                                  builder: (context) => const RegisterPage()),
                              (route) => false,
                            );
                          })
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
    } else {
      return Padding(
        padding: const EdgeInsets.only(top: 70),
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
                  text: TextSpan(children: [
                    const TextSpan(
                        text: 'Don\'t you have an account? ',
                        style: TextStyle(color: Colors.black)),
                    TextSpan(
                        text: 'Sign up',
                        style: const TextStyle(
                            color: Color.fromARGB(1000, 18, 170, 115)),
                        recognizer: TapGestureRecognizer()
                          ..onTap = () {
                            Navigator.pushAndRemoveUntil(
                              context,
                              MaterialPageRoute(
                                  builder: (context) => const RegisterPage()),
                              (route) => false,
                            );
                          })
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
}
