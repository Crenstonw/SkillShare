import 'package:skillshare_flutter/models/dtos/register_dto.dart';
import 'package:skillshare_flutter/models/register_response.dart';

abstract class RegisterRepository {
  Future<RegisterResponse> register(RegisterDto registerDto);
}