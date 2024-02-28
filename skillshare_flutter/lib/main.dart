import 'package:flutter/material.dart';
import 'package:skillshare_flutter/environments/local_storage.dart';
import 'package:skillshare_flutter/ui/login_page.dart';
import 'package:skillshare_flutter/ui/register_page.dart';

void main() async {
  runApp(const MainApp());
  await Localstorage.configurePrefs();
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(home: RegisterPage());
  }
}
