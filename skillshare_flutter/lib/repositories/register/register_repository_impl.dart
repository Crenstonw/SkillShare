import 'dart:convert';

import 'package:http/http.dart';
import 'package:skillshare_flutter/models/dtos/register_dto.dart';
import 'package:skillshare_flutter/models/register_response.dart';
import 'package:skillshare_flutter/repositories/register/register_repository.dart';

class RegisterRepositoryImpl extends RegisterRepository {
  final Client _httpClient = Client();

  @override
  Future<RegisterResponse> register(RegisterDto registerDto) async {
    final response = await _httpClient.post(
        Uri.parse('http://localhost:8080/auth/register'),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8'
        },
        body: registerDto.toJson());
    if (response.statusCode == 200) {
      return RegisterResponse.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Failed to register');
    }
  }
}
