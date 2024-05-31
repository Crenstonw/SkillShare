import 'dart:convert';

OrderListResponse orderListResponseFromJson(String str) =>
    OrderListResponse.fromJson(json.decode(str));

String orderListResponseToJson(OrderListResponse data) =>
    json.encode(data.toJson());

class OrderListResponse {
  List<Order> orders;
  Pageable pageable;
  bool last;
  int totalElements;
  int totalPages;
  bool first;
  int size;
  int number;
  Sort sort;
  int numberOfElements;
  bool empty;

  OrderListResponse({
    required this.orders,
    required this.pageable,
    required this.last,
    required this.totalElements,
    required this.totalPages,
    required this.first,
    required this.size,
    required this.number,
    required this.sort,
    required this.numberOfElements,
    required this.empty,
  });

  factory OrderListResponse.fromJson(Map<String, dynamic> json) =>
      OrderListResponse(
        orders: List<Order>.from(json["orders"].map((x) => Order.fromJson(x))),
        pageable: Pageable.fromJson(json["pageable"]),
        last: json["last"],
        totalElements: json["totalElements"],
        totalPages: json["totalPages"],
        first: json["first"],
        size: json["size"],
        number: json["number"],
        sort: Sort.fromJson(json["sort"]),
        numberOfElements: json["numberOfElements"],
        empty: json["empty"],
      );

  Map<String, dynamic> toJson() => {
        "orders": List<dynamic>.from(orders.map((x) => x.toJson())),
        "pageable": pageable.toJson(),
        "last": last,
        "totalElements": totalElements,
        "totalPages": totalPages,
        "first": first,
        "size": size,
        "number": number,
        "sort": sort.toJson(),
        "numberOfElements": numberOfElements,
        "empty": empty,
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

class Pageable {
  int pageNumber;
  int pageSize;
  Sort sort;
  int offset;
  bool unpaged;
  bool paged;

  Pageable({
    required this.pageNumber,
    required this.pageSize,
    required this.sort,
    required this.offset,
    required this.unpaged,
    required this.paged,
  });

  factory Pageable.fromJson(Map<String, dynamic> json) => Pageable(
        pageNumber: json["pageNumber"],
        pageSize: json["pageSize"],
        sort: Sort.fromJson(json["sort"]),
        offset: json["offset"],
        unpaged: json["unpaged"],
        paged: json["paged"],
      );

  Map<String, dynamic> toJson() => {
        "pageNumber": pageNumber,
        "pageSize": pageSize,
        "sort": sort.toJson(),
        "offset": offset,
        "unpaged": unpaged,
        "paged": paged,
      };
}

class Sort {
  bool empty;
  bool unsorted;
  bool sorted;

  Sort({
    required this.empty,
    required this.unsorted,
    required this.sorted,
  });

  factory Sort.fromJson(Map<String, dynamic> json) => Sort(
        empty: json["empty"],
        unsorted: json["unsorted"],
        sorted: json["sorted"],
      );

  Map<String, dynamic> toJson() => {
        "empty": empty,
        "unsorted": unsorted,
        "sorted": sorted,
      };
}
