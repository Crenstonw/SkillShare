import 'dart:convert';

UserResponse userResponseFromJson(String str) => UserResponse.fromJson(json.decode(str));

String userResponseToJson(UserResponse data) => json.encode(data.toJson());

class UserResponse {
    String id;
    String email;
    String username;
    String name;
    String surname;
    String password;
    String profilePicture;
    String userRole;
    DateTime createdAt;
    bool enabled;

    UserResponse({
        required this.id,
        required this.email,
        required this.username,
        required this.name,
        required this.surname,
        required this.password,
        required this.profilePicture,
        required this.userRole,
        required this.createdAt,
        required this.enabled,
    });

    factory UserResponse.fromJson(Map<String, dynamic> json) => UserResponse(
        id: json["id"],
        email: json["email"],
        username: json["username"],
        name: json["name"],
        surname: json["surname"],
        password: json["password"],
        profilePicture: json["profilePicture"],
        userRole: json["userRole"],
        createdAt: DateTime.parse(json["createdAt"]),
        enabled: json["enabled"],
    );

    Map<String, dynamic> toJson() => {
        "id": id,
        "email": email,
        "username": username,
        "name": name,
        "surname": surname,
        "password": password,
        "profilePicture": profilePicture,
        "userRole": userRole,
        "createdAt": createdAt.toIso8601String(),
        "enabled": enabled,
    };
}
