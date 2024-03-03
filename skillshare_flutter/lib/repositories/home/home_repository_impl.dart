import 'dart:convert';

import 'package:http/http.dart';
import 'package:skillshare_flutter/environments/local_storage.dart';import 'package:skillshare_flutter/models/user_response.dart';
import 'package:skillshare_flutter/repositories/home/home_repository.dart';

class HomeRepositoryImpl extends HomeRepository {
  final Client _httpClient = Client();

  @override
  Future<String> profilePicture() async {
    final response = await _httpClient
        .get(Uri.parse('http://10.0.2.2:8080/user/me'), headers: <String, String>{
      'Content-type': 'application/json',
      'Authorization': 'Bearer ${Localstorage.prefs.getString('token')}'
    });
    if (response.statusCode == 200) {
      final Map<String, dynamic> responseData = json.decode(response.body);
        final String profilePictureUrl = responseData['profilePicture'] as String;
        return profilePictureUrl;
    } else {
      throw Exception('Failed to find the user');
    }
  }
}
