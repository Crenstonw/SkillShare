import 'dart:convert';

EditUserRequest editUserRequestFromJson(String str) => EditUserRequest.fromJson(json.decode(str));

String editUserRequestToJson(EditUserRequest data) => json.encode(data.toJson());

class EditUserRequest {
    String name;
    String surname;
    String profilePicture;
    String username;
    String email;

    EditUserRequest({
        required this.name,
        required this.surname,
        required this.profilePicture,
        required this.username,
        required this.email,
    });

    factory EditUserRequest.fromJson(Map<String, dynamic> json) => EditUserRequest(
        name: json["name"],
        surname: json["surname"],
        profilePicture: json["profilePicture"],
        username: json["username"],
        email: json["email"],
    );

    Map<String, dynamic> toJson() => {
        "name": name,
        "surname": surname,
        "profilePicture": profilePicture,
        "username": username,
        "email": email,
    };
}