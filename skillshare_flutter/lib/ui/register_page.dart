import 'package:flutter/material.dart';

class RegisterPage extends StatefulWidget {
  const RegisterPage({super.key});

  @override
  State<RegisterPage> createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          const Text('Register'),
          TextFormField(
            decoration: const InputDecoration(
              labelText: 'nombre',
            ),
            onSaved: (String? value) {},
          )
        ],
      ),
    );
  }
}
