import 'dart:convert';

import 'package:http/http.dart';
import 'package:skillshare_flutter/environments/local_storage.dart';
import 'package:skillshare_flutter/models/dtos/login_dto.dart';
import 'package:skillshare_flutter/models/login_response.dart';
import 'package:skillshare_flutter/repositories/login/login_repository.dart';

class LoginRepositoryImpl extends LoginRepository {
  final Client _httpClient = Client();

  @override
  Future<LoginResponse> login(LoginDto loginDto) async {
    final jsonBody = json.encode(loginDto.toJson());
    final response =
        await _httpClient.post(Uri.parse('http://10.0.2.2:8080/auth/login'),
            headers: <String, String>{
              'Content-Type': 'application/json',
            },
            body: jsonBody);
    if (response.statusCode == 201) {
      final finalResponse = LoginResponse.fromJson(response.body);
      Localstorage.prefs.setString('token', finalResponse.token!);

      return finalResponse;
    } else {
      throw Exception('Failed to login');
    }
  }
}
