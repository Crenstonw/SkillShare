import 'package:flutter/material.dart';
import 'package:skillshare_flutter/ui/register_page.dart';

void main() {
  runApp(const MainApp());
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const RegisterPage();
  }
}
