import 'dart:convert';

OrderEditRequest orderEditRequestFromJson(String str) => OrderEditRequest.fromJson(json.decode(str));

String orderEditRequestToJson(OrderEditRequest data) => json.encode(data.toJson());

class OrderEditRequest {
    String title;
    String description;
    List<String> tags;
    double price;

    OrderEditRequest({
        required this.title,
        required this.description,
        required this.tags,
        required this.price,
    });

    factory OrderEditRequest.fromJson(Map<String, dynamic> json) => OrderEditRequest(
        title: json["title"],
        description: json["description"],
        tags: List<String>.from(json["tags"].map((x) => x)),
        price: json["price"]?.toDouble(),
    );

    Map<String, dynamic> toJson() => {
        "title": title,
        "description": description,
        "tags": List<dynamic>.from(tags.map((x) => x)),
        "price": price,
    };
}