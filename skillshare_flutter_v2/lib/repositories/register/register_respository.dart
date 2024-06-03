import 'package:skillshare_flutter_v2/models/dto/register_request.dart';
import 'package:skillshare_flutter_v2/models/register_response.dart';

abstract class RegisterRepository {
  Future<RegisterResponse> register(RegisterRequest registerRequest);
}