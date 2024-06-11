import 'dart:convert';

OrderEditResponse orderEditResponseFromJson(String str) => OrderEditResponse.fromJson(json.decode(str));

String orderEditResponseToJson(OrderEditResponse data) => json.encode(data.toJson());

class OrderEditResponse {
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

    OrderEditResponse({
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
    });

    factory OrderEditResponse.fromJson(Map<String, dynamic> json) => OrderEditResponse(
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
