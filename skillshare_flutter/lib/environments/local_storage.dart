import 'package:shared_preferences/shared_preferences.dart';
//https://www.youtube.com/watch?v=GGNiT9PgrFQ video de referencia sobre el LocalStorage

class Localstorage {
  static late SharedPreferences prefs;

  static Future<void> configurePrefs() async {
    prefs = await SharedPreferences.getInstance();
  }
}
