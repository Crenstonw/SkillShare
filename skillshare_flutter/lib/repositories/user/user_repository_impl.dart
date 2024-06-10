import 'dart:convert';

import 'package:http/http.dart';
import 'package:skillshare_flutter/environments/local_storage.dart';
import 'package:skillshare_flutter/models/responses/user_response.dart';
import 'package:skillshare_flutter/repositories/user/user_repository.dart';

class UserRepositoryImpl extends UserRepository {
  final Client _httpClient = Client();

  @override
  Future<UserResponse> me() async {
    final response =
        await _httpClient.get(Uri.parse('http://10.0.2.2:8080/user/me'),
            headers: <String, String>{
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ${Localstorage.prefs.getString('token')}'
            });
    if (response.statusCode == 200) {
      final finalResponse = UserResponse.fromJson(json.decode(response.body));
      return finalResponse;
    } else {
      throw Exception('Failed to get the information');
    }
  }
}