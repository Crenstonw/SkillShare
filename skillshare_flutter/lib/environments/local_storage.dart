import 'package:shared_preferences/shared_preferences.dart';

class Localstorage {
  late SharedPreferences prefs;

  Future<void> configurePrefs() async {
    prefs = await SharedPreferences.getInstance();
  }
}
