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
      body: Column(
        children: [
          const Text('Register'),
          Card(
            child: TextFormField(
              decoration: const InputDecoration(
                labelText: 'email',
              ),
              onChanged: (value) => email = value,
            ),
          ),
          Card(
            child: TextFormField(
              decoration: const InputDecoration(
                labelText: 'name',
              ),
              onChanged: (value) => name = value,
            ),
          ),
          Card(
            child: TextFormField(
              decoration: const InputDecoration(
                labelText: 'surname',
              ),
              onChanged: (value) => surname = value,
            ),
          ),
          Card(
            child: TextFormField(
              decoration: const InputDecoration(
                labelText: 'password',
              ),
              onChanged: (value) => password = value,
            ),
          ),
          ElevatedButton(
                onPressed: () {
                  registerRepository = RegisterRepositoryImpl();
                  _registerBloc = RegisterBloc(registerRepository)..add(RegisterFetch(email, name, surname, password));
                },
                child: Text('Register'),
              ),
        ],
      ),
    );
  }
}
