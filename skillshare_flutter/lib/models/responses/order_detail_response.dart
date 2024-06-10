import 'dart:convert';

OrderDetailResponse orderDetailResponseFromJson(String str) => OrderDetailResponse.fromJson(json.decode(str));

String orderDetailResponseToJson(OrderDetailResponse data) => json.encode(data.toJson());

class OrderDetailResponse {
    String id;
    String title;
    String description;
    double price;
    String state;
    DateTime createdAt;
    DateTime lastTimeModified;
    bool isAboutToExpire;
    User user;
    List<Tag> tags;
    List<Message> messages;

    OrderDetailResponse({
        required this.id,
        required this.title,
        required this.description,
        required this.price,
        required this.state,
        required this.createdAt,
        required this.lastTimeModified,
        required this.isAboutToExpire,
        required this.user,
        required this.tags,
        required this.messages,
    });

    factory OrderDetailResponse.fromJson(Map<String, dynamic> json) => OrderDetailResponse(
        id: json["id"],
        title: json["title"],
        description: json["description"],
        price: json["price"]?.toDouble(),
        state: json["state"],
        createdAt: DateTime.parse(json["createdAt"]),
        lastTimeModified: DateTime.parse(json["lastTimeModified"]),
        isAboutToExpire: json["isAboutToExpire"],
        user: User.fromJson(json["user"]),
        tags: List<Tag>.from(json["tags"].map((x) => Tag.fromJson(x))),
        messages: List<Message>.from(json["messages"].map((x) => Message.fromJson(x))),
    );

    Map<String, dynamic> toJson() => {
        "id": id,
        "title": title,
        "description": description,
        "price": price,
        "state": state,
        "createdAt": createdAt.toIso8601String(),
        "lastTimeModified": lastTimeModified.toIso8601String(),
        "isAboutToExpire": isAboutToExpire,
        "user": user.toJson(),
        "tags": List<dynamic>.from(tags.map((x) => x.toJson())),
        "messages": List<dynamic>.from(messages.map((x) => x.toJson())),
    };
}

class Message {
    String id;
    String title;
    String message;
    bool isMyMessage;
    DateTime dateTime;
    Author author;

    Message({
        required this.id,
        required this.title,
        required this.message,
        required this.isMyMessage,
        required this.dateTime,
        required this.author,
    });

    factory Message.fromJson(Map<String, dynamic> json) => Message(
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

class Tag {
    String id;
    String name;

    Tag({
        required this.id,
        required this.name,
    });

    factory Tag.fromJson(Map<String, dynamic> json) => Tag(
        id: json["id"],
        name: json["name"],
    );

    Map<String, dynamic> toJson() => {
        "id": id,
        "name": name,
    };
}

class User {
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

    User({
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

    factory User.fromJson(Map<String, dynamic> json) => User(
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