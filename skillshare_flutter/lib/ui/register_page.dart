import 'package:flutter/material.dart';
import 'package:skillshare_flutter/blocs/register/register_bloc.dart';
import 'package:skillshare_flutter/repositories/register/register_repository.dart';
import 'package:skillshare_flutter/repositories/register/register_repository_impl.dart';

class RegisterPage extends StatefulWidget {
  const RegisterPage({super.key});

  @override
  State<RegisterPage> createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  late RegisterRepository registerRepository;
  late RegisterBloc _registerBloc;

  late String email;
  late String name;
  late String surname;
  late String password;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color.fromARGB(1000, 191, 218, 208),
      body: Padding(
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
                        style: TextStyle(
                            color: Color.fromARGB(1000, 18, 170, 115)))
                  ]),
                ),
              ),
              ElevatedButton(
                onPressed: () {
                  registerRepository = RegisterRepositoryImpl();
                  _registerBloc = RegisterBloc(registerRepository)
                    ..add(RegisterFetch(email, name, surname, password));
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
      ),
    );
  }
}
