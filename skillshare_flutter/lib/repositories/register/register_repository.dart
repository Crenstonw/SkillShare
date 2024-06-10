import 'package:skillshare_flutter/models/dtos/register_request.dart';
import 'package:skillshare_flutter/models/responses/register_response.dart';

abstract class RegisterRepository {
  Future<RegisterResponse> register(RegisterRequest registerDto);
}
