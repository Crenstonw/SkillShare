import 'package:flutter/material.dart';
import 'package:skillshare_flutter_v2/environments/local_storage.dart';
import 'package:skillshare_flutter_v2/ui/login_page.dart';

void main() async {
  runApp(const MainApp());
  await Localstorage.configurePrefs();
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(home: LoginPage());
  }
}
