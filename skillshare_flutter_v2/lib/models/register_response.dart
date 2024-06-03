import 'dart:convert';

RegisterResponse registerResponseFromJson(String str) => RegisterResponse.fromJson(json.decode(str));

String registerResponseToJson(RegisterResponse data) => json.encode(data.toJson());

class RegisterResponse {
    String id;
    String email;
    String username;
    String createdAt;
    String token;
    bool admin;

    RegisterResponse({
        required this.id,
        required this.email,
        required this.username,
        required this.createdAt,
        required this.token,
        required this.admin,
    });

    factory RegisterResponse.fromJson(Map<String, dynamic> json) => RegisterResponse(
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