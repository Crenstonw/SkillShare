import 'dart:convert';

class UserResponse {
  String? id;
  String? email;
  String? username;
  String? name;
  String? surname;
  String? password;
  dynamic profilePicture;
  String? userRole;
  DateTime? createdAt;
  bool? enabled;

  UserResponse({
    this.id,
    this.email,
    this.username,
    this.name,
    this.surname,
    this.password,
    this.profilePicture,
    this.userRole,
    this.createdAt,
    this.enabled,
  });

  factory UserResponse.fromMap(Map<String, dynamic> data) => UserResponse(
        id: data['id'] as String?,
        email: data['email'] as String?,
        username: data['username'] as String?,
        name: data['name'] as String?,
        surname: data['surname'] as String?,
        password: data['password'] as String?,
        profilePicture: data['profilePicture'] as dynamic,
        userRole: data['userRole'] as String?,
        createdAt: data['createdAt'] == null
            ? null
            : DateTime.parse(data['createdAt'] as String),
        enabled: data['enabled'] as bool?,
      );

  Map<String, dynamic> toMap() => {
        'id': id,
        'email': email,
        'username': username,
        'name': name,
        'surname': surname,
        'password': password,
        'profilePicture': profilePicture,
        'userRole': userRole,
        'createdAt': createdAt?.toIso8601String(),
        'enabled': enabled,
      };

  /// `dart:convert`
  ///
  /// Parses the string and returns the resulting Json object as [UserResponse].
  factory UserResponse.fromJson(String data) {
    return UserResponse.fromMap(json.decode(data) as Map<String, dynamic>);
  }

  /// `dart:convert`
  ///
  /// Converts [UserResponse] to a JSON string.
  String toJson() => json.encode(toMap());
}
