import 'package:skillshare_flutter/models/responses/user_response.dart';

abstract class UserRepository {
  Future<UserResponse> me();
}