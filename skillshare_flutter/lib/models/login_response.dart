import 'dart:convert';

class LoginResponse {
  String? id;
  String? email;
  String? username;
  String? createdAt;
  String? token;

  LoginResponse({
    this.id,
    this.email,
    this.username,
    this.createdAt,
    this.token,
  });

  factory LoginResponse.fromMap(Map<String, dynamic> data) => LoginResponse(
        id: data['id'] as String?,
        email: data['email'] as String?,
        username: data['username'] as String?,
        createdAt: data['createdAt'] as String?,
        token: data['token'] as String?,
      );

  Map<String, dynamic> toMap() => {
        'id': id,
        'email': email,
        'username': username,
        'createdAt': createdAt,
        'token': token,
      };

  /// `dart:convert`
  ///
  /// Parses the string and returns the resulting Json object as [LoginResponse].
  factory LoginResponse.fromJson(String data) {
    return LoginResponse.fromMap(json.decode(data) as Map<String, dynamic>);
  }

  /// `dart:convert`
  ///
  /// Converts [LoginResponse] to a JSON string.
  String toJson() => json.encode(toMap());
}
