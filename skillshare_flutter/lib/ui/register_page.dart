import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:skillshare_flutter/blocs/register/register_bloc.dart';
import 'package:skillshare_flutter/repositories/register/register_repository.dart';
import 'package:skillshare_flutter/repositories/register/register_repository_impl.dart';

class RegisterPage extends StatefulWidget {
  const RegisterPage({super.key});

  @override
  State<RegisterPage> createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  final userTextController = TextEditingController();
  final passTextController = TextEditingController();

  late RegisterRepository registerRepository;
  late RegisterBloc _registerBloc;

  late String email;
  late String name;
  late String surname;
  late String password;

  @override
  void initState() {
    registerRepository = RegisterRepositoryImpl();
    _registerBloc = RegisterBloc(registerRepository);
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
      value: _registerBloc,
      child: Scaffold(
        backgroundColor: const Color.fromARGB(1000, 191, 218, 208),
        body: Padding(
          padding: const EdgeInsets.all(20.0),
          child: BlocConsumer<RegisterBloc, RegisterState>(
            buildWhen: (context, state) {
              return state is RegisterInitial ||
                  state is RegisterFetchSuccess ||
                  state is RegisterFetchError ||
                  state is RegisterLoading;
            },
            builder: (context, state) {
              if (state is RegisterFetchSuccess) {
                return const Text('Register success');
              } else if (state is RegisterFetchError) {
                return const Text('something went wrong');
              } else if (state is RegisterLoading) {
                return const Center(child: CircularProgressIndicator());
              }
              return Center(child: _buildRegisterForm());
            },
            /*listenWhen: (context, state) {
              return state is GetRequestTokenSuccess;
            },*/
            listener: (BuildContext context, RegisterState state) {},
          ),
        ),
      ),
    );
  }

  _buildRegisterForm() {
    return Padding(
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
                    labelText: 'name',
                  ),
                  onChanged: (value) => name = value,
                ),
              ),
            ),
            Card(
              child: Padding(
                padding: const EdgeInsets.all(16),
                child: TextFormField(
                  decoration: const InputDecoration(
                    labelText: 'surname',
                  ),
                  onChanged: (value) => surname = value,
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
                      text: 'Already have an account? ',
                      style: TextStyle(color: Colors.black)),
                  TextSpan(
                      text: 'Sign in',
                      style:
                          TextStyle(color: Color.fromARGB(1000, 18, 170, 115)))
                ]),
              ),
            ),
            ElevatedButton(
              onPressed: () {
                _registerBloc.add(RegisterFetch(email, name, surname, password));
              },
              child: const Text(
                'Get Started',
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
