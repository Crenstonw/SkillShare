import 'dart:convert';

NewOrderMessageResponse newOrderMessageResponseFromJson(String str) => NewOrderMessageResponse.fromJson(json.decode(str));

String newOrderMessageResponseToJson(NewOrderMessageResponse data) => json.encode(data.toJson());

class NewOrderMessageResponse {
    String id;
    String title;
    String message;
    bool isMyMessage;
    DateTime dateTime;
    Author author;

    NewOrderMessageResponse({
        required this.id,
        required this.title,
        required this.message,
        required this.isMyMessage,
        required this.dateTime,
        required this.author,
    });

    factory NewOrderMessageResponse.fromJson(Map<String, dynamic> json) => NewOrderMessageResponse(
        id: json["id"],
        title: json["title"],
        message: json["message"],
        isMyMessage: json["isMyMessage"],
        dateTime: DateTime.parse(json["dateTime"]),
        author: Author.fromJson(json["author"]),
    );

    Map<String, dynamic> toJson() => {
        "id": id,
        "title": title,
        "message": message,
        "isMyMessage": isMyMessage,
        "dateTime": dateTime.toIso8601String(),
        "author": author.toJson(),
    };
}

class Author {
    String id;
    String email;
    String username;
    String createdAt;
    bool admin;

    Author({
        required this.id,
        required this.email,
        required this.username,
        required this.createdAt,
        required this.admin,
    });

    factory Author.fromJson(Map<String, dynamic> json) => Author(
        id: json["id"],
        email: json["email"],
        username: json["username"],
        createdAt: json["createdAt"],
        admin: json["admin"],
    );

    Map<String, dynamic> toJson() => {
        "id": id,
        "email": email,
        "username": username,
        "createdAt": createdAt,
        "admin": admin,
    };
}