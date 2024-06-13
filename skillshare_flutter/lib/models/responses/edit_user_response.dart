import 'dart:convert';

EditUserResponse editUserResponseFromJson(String str) => EditUserResponse.fromJson(json.decode(str));

String editUserResponseToJson(EditUserResponse data) => json.encode(data.toJson());

class EditUserResponse {
    String id;
    String email;
    String username;
    String name;
    String surname;
    String password;
    String profilePicture;
    List<String> role;
    List<Order> orders;
    List<dynamic> favoriteOrders;
    bool enabled;

    EditUserResponse({
        required this.id,
        required this.email,
        required this.username,
        required this.name,
        required this.surname,
        required this.password,
        required this.profilePicture,
        required this.role,
        required this.orders,
        required this.favoriteOrders,
        required this.enabled,
    });

    factory EditUserResponse.fromJson(Map<String, dynamic> json) => EditUserResponse(
        id: json["id"],
        email: json["email"],
        username: json["username"],
        name: json["name"],
        surname: json["surname"],
        password: json["password"],
        profilePicture: json["profilePicture"],
        role: List<String>.from(json["role"].map((x) => x)),
        orders: List<Order>.from(json["orders"].map((x) => Order.fromJson(x))),
        favoriteOrders: List<dynamic>.from(json["favoriteOrders"].map((x) => x)),
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
        "role": List<dynamic>.from(role.map((x) => x)),
        "orders": List<dynamic>.from(orders.map((x) => x.toJson())),
        "favoriteOrders": List<dynamic>.from(favoriteOrders.map((x) => x)),
        "enabled": enabled,
    };
}

class Order {
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

    Order({
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

    factory Order.fromJson(Map<String, dynamic> json) => Order(
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
