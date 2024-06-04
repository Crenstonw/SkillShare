import 'dart:convert';

LoginResponse loginResponseFromJson(String str) => LoginResponse.fromJson(json.decode(str));

String loginResponseToJson(LoginResponse data) => json.encode(data.toJson());

class LoginResponse {
    String id;
    String email;
    String username;
    String createdAt;
    String token;
    bool admin;

    LoginResponse({
        required this.id,
        required this.email,
        required this.username,
        required this.createdAt,
        required this.token,
        required this.admin,
    });

    factory LoginResponse.fromJson(Map<String, dynamic> json) => LoginResponse(
        id: json["id"],
        email: json["email"],
        username: json["username"],
        createdAt: json["createdAt"],
        token: json["token"],
        admin: json["admin"],
    );

    Map<String, dynamic> toJson() => {
        "id": id,
        "email": email,
        "username": username,
        "createdAt": createdAt,
        "token": token,
        "admin": admin,
    };
}
