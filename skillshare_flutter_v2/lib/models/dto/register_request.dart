import 'dart:convert';

RegisterRequest registerRequestFromJson(String str) => RegisterRequest.fromJson(json.decode(str));

String registerRequestToJson(RegisterRequest data) => json.encode(data.toJson());

class RegisterRequest {
    String email;
    String username;
    String name;
    String surname;
    String password;

    RegisterRequest({
        required this.email,
        required this.username,
        required this.name,
        required this.surname,
        required this.password,
    });

    factory RegisterRequest.fromJson(Map<String, dynamic> json) => RegisterRequest(
        email: json["email"],
        username: json["username"],
        name: json["name"],
        surname: json["surname"],
        password: json["password"],
    );

    Map<String, dynamic> toJson() => {
        "email": email,
        "username": username,
        "name": name,
        "surname": surname,
        "password": password,
    };
}