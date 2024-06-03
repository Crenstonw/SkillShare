import 'dart:convert';

import 'package:http/http.dart';
import 'package:skillshare_flutter/environments/local_storage.dart';
import 'package:skillshare_flutter/models/dtos/register_request.dart';
import 'package:skillshare_flutter/models/register_response.dart';
import 'package:skillshare_flutter/repositories/register/register_repository.dart';

class RegisterRepositoryImpl extends RegisterRepository {
  final Client _httpClient = Client();

  @override
  Future<RegisterResponse> register(RegisterRequest registerDto) async {
    final jsonBody = json.encode(registerDto.toJson());
    final response =
        await _httpClient.post(Uri.parse('http://10.0.2.2:8080/auth/register'),
            headers: <String, String>{
              'Content-Type': 'application/json',
            },
            body: jsonBody);
    if (response.statusCode == 201) {
      final finalResponse = RegisterResponse.fromJson(json.decode(response.body));
      Localstorage.prefs.setString('token', finalResponse.token);
      return finalResponse;
    } else {
      throw Exception('Failed to register');
    }
  }
}
