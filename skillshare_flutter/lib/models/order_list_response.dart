import 'dart:convert';

class OrderListResponse {
  List<Order>? orders;

  OrderListResponse({this.orders});

  factory OrderListResponse.fromMap(Map<String, dynamic> data) {
    return OrderListResponse(
      orders: (data['orders'] as List<dynamic>?)
          ?.map((e) => Order.fromMap(e as Map<String, dynamic>))
          .toList(),
    );
  }

  Map<String, dynamic> toMap() => {
        'orders': orders?.map((e) => e.toMap()).toList(),
      };

  /// `dart:convert`
  ///
  /// Parses the string and returns the resulting Json object as [OrderListResponse].
  factory OrderListResponse.fromJson(String data) {
    return OrderListResponse.fromMap(json.decode(data) as Map<String, dynamic>);
  }

  /// `dart:convert`
  ///
  /// Converts [OrderListResponse] to a JSON string.
  String toJson() => json.encode(toMap());
}

class Order {
  String? id;
  String? title;
  String? description;
  User? user;
  List<dynamic>? tags;

  Order({this.id, this.title, this.description, this.user, this.tags});

  factory Order.fromMap(Map<String, dynamic> data) => Order(
        id: data['id'] as String?,
        title: data['title'] as String?,
        description: data['description'] as String?,
        user: data['user'] == null
            ? null
            : User.fromMap(data['user'] as Map<String, dynamic>),
        tags: data['tags'] as List<dynamic>?,
      );

  Map<String, dynamic> toMap() => {
        'id': id,
        'title': title,
        'description': description,
        'user': user?.toMap(),
        'tags': tags,
      };

  /// `dart:convert`
  ///
  /// Parses the string and returns the resulting Json object as [Order].
  factory Order.fromJson(String data) {
    return Order.fromMap(json.decode(data) as Map<String, dynamic>);
  }

  /// `dart:convert`
  ///
  /// Converts [Order] to a JSON string.
  String toJson() => json.encode(toMap());
}

class User {
  String? id;
  String? email;
  String? username;
  String? name;
  String? surname;
  String? password;
  String? profilePicture;
  String? userRole;
  DateTime? createdAt;
  bool? enabled;

  User({
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

  factory User.fromMap(Map<String, dynamic> data) => User(
        id: data['id'] as String?,
        email: data['email'] as String?,
        username: data['username'] as String?,
        name: data['name'] as String?,
        surname: data['surname'] as String?,
        password: data['password'] as String?,
        profilePicture: data['profilePicture'] as String?,
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
  /// Parses the string and returns the resulting Json object as [User].
  factory User.fromJson(String data) {
    return User.fromMap(json.decode(data) as Map<String, dynamic>);
  }

  /// `dart:convert`
  ///
  /// Converts [User] to a JSON string.
  String toJson() => json.encode(toMap());
}
