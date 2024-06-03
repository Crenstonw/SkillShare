import 'dart:convert';

import 'package:http/http.dart';
import 'package:skillshare_flutter_v2/environments/local_storage.dart';
import 'package:skillshare_flutter_v2/models/dto/register_request.dart';
import 'package:skillshare_flutter_v2/models/register_response.dart';
import 'package:skillshare_flutter_v2/repositories/register/register_respository.dart';

class RegisterRepositoryImpl implements RegisterRepository {
  final Client _httpClient = Client();

  @override
  Future<RegisterResponse> register(RegisterRequest registerRequest) async {
    final jsonBody = json.encode(registerRequest.toJson());
    final response =
        await _httpClient.post(Uri.parse('http://10.0.2.2:8080/auth/register'),
            headers: <String, String>{
              'Content-Type': 'application/json',
            },
            body: jsonBody);
    if (response.statusCode == 201) {
      final finalResponse = RegisterResponse.fromJson(response.body as Map<String, dynamic>);
      Localstorage.prefs.setString('token', finalResponse.token);
      return finalResponse;
    } else {
      throw Exception('Failed to register');
    }
  }

}