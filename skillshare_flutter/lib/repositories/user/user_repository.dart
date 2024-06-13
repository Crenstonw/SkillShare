import 'package:skillshare_flutter/models/dtos/edit_user_request.dart';
import 'package:skillshare_flutter/models/responses/edit_user_response.dart';
import 'package:skillshare_flutter/models/responses/user_response.dart';

abstract class UserRepository {
  Future<UserResponse> me();
  Future<EditUserResponse> editUser(String id, EditUserRequest editUserRequest);
}