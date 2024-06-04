import 'package:skillshare_flutter/models/dtos/login_request.dart';
import 'package:skillshare_flutter/models/responses/login_response.dart';

abstract class LoginRepository {
  Future<LoginResponse> login(LoginRequest loginRequest);
}
