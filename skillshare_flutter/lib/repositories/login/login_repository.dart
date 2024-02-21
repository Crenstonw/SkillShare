import 'package:skillshare_flutter/models/dtos/login_dto.dart';
import 'package:skillshare_flutter/models/login_response.dart';

abstract class LoginRepository {
  Future<LoginResponse> login(LoginDto loginDto);
}
