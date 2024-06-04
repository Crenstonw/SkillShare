import 'dart:convert';

AllOrderResponse allOrderResponseFromJson(String str) => AllOrderResponse.fromJson(json.decode(str));

String allOrderResponseToJson(AllOrderResponse data) => json.encode(data.toJson());

class AllOrderResponse {
    List<Content> content;
    Pageable pageable;
    int totalPages;
    int totalElements;
    bool last;
    int number;
    int size;
    int numberOfElements;
    Sort sort;
    bool first;
    bool empty;

    AllOrderResponse({
        required this.content,
        required this.pageable,
        required this.totalPages,
        required this.totalElements,
        required this.last,
        required this.number,
        required this.size,
        required this.numberOfElements,
        required this.sort,
        required this.first,
        required this.empty,
    });

    factory AllOrderResponse.fromJson(Map<String, dynamic> json) => AllOrderResponse(
        content: List<Content>.from(json["content"].map((x) => Content.fromJson(x))),
        pageable: Pageable.fromJson(json["pageable"]),
        totalPages: json["totalPages"],
        totalElements: json["totalElements"],
        last: json["last"],
        number: json["number"],
        size: json["size"],
        numberOfElements: json["numberOfElements"],
        sort: Sort.fromJson(json["sort"]),
        first: json["first"],
        empty: json["empty"],
    );

    Map<String, dynamic> toJson() => {
        "content": List<dynamic>.from(content.map((x) => x.toJson())),
        "pageable": pageable.toJson(),
        "totalPages": totalPages,
        "totalElements": totalElements,
        "last": last,
        "number": number,
        "size": size,
        "numberOfElements": numberOfElements,
        "sort": sort.toJson(),
        "first": first,
        "empty": empty,
    };
}

class Content {
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

    Content({
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

    factory Content.fromJson(Map<String, dynamic> json) => Content(
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
    bool paged;
    bool unpaged;

    Pageable({
        required this.pageNumber,
        required this.pageSize,
        required this.sort,
        required this.offset,
        required this.paged,
        required this.unpaged,
    });

    factory Pageable.fromJson(Map<String, dynamic> json) => Pageable(
        pageNumber: json["pageNumber"],
        pageSize: json["pageSize"],
        sort: Sort.fromJson(json["sort"]),
        offset: json["offset"],
        paged: json["paged"],
        unpaged: json["unpaged"],
    );

    Map<String, dynamic> toJson() => {
        "pageNumber": pageNumber,
        "pageSize": pageSize,
        "sort": sort.toJson(),
        "offset": offset,
        "paged": paged,
        "unpaged": unpaged,
    };
}

class Sort {
    bool sorted;
    bool empty;
    bool unsorted;

    Sort({
        required this.sorted,
        required this.empty,
        required this.unsorted,
    });

    factory Sort.fromJson(Map<String, dynamic> json) => Sort(
        sorted: json["sorted"],
        empty: json["empty"],
        unsorted: json["unsorted"],
    );

    Map<String, dynamic> toJson() => {
        "sorted": sorted,
        "empty": empty,
        "unsorted": unsorted,
    };
}
