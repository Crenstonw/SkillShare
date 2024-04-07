import 'dart:convert';

class RegisterResponse {
  String? id;
  String? email;
  String? username;
  String? createdAt;
  String? token;

  RegisterResponse({
    this.id,
    this.email,
    this.username,
    this.createdAt,
    this.token,
  });

  factory RegisterResponse.fromMap(Map<String, dynamic> data) {
    return RegisterResponse(
      id: data['id'] as String?,
      email: data['email'] as String?,
      username: data['username'] as String?,
      createdAt: data['createdAt'] as String?,
      token: data['token'] as String?,
    );
  }

  Map<String, dynamic> toMap() => {
        'id': id,
        'email': email,
        'username': username,
        'createdAt': createdAt,
        'token': token,
      };

  /// `dart:convert`
  ///
  /// Parses the string and returns the resulting Json object as [RegisterResponse].
  factory RegisterResponse.fromJson(String data) {
    return RegisterResponse.fromMap(json.decode(data) as Map<String, dynamic>);
  }

  /// `dart:convert`
  ///
  /// Converts [RegisterResponse] to a JSON string.
  String toJson() => json.encode(toMap());
}
