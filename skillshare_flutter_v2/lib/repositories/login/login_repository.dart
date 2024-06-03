import 'package:skillshare_flutter_v2/models/dto/login_request.dart';
import 'package:skillshare_flutter_v2/models/login_response.dart';

abstract class LoginRepository {
  Future<LoginResponse> login(LoginRequest loginDto);
}
