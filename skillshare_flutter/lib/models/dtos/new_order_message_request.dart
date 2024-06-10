import 'dart:convert';

NewOrderMessageRequest newOrderMessageRequestFromJson(String str) => NewOrderMessageRequest.fromJson(json.decode(str));

String newOrderMessageRequestToJson(NewOrderMessageRequest data) => json.encode(data.toJson());

class NewOrderMessageRequest {
    String title;
    String message;
    String orderId;

    NewOrderMessageRequest({
        required this.title,
        required this.message,
        required this.orderId,
    });

    factory NewOrderMessageRequest.fromJson(Map<String, dynamic> json) => NewOrderMessageRequest(
        title: json["title"],
        message: json["message"],
        orderId: json["orderId"],
    );

    Map<String, dynamic> toJson() => {
        "title": title,
        "message": message,
        "orderId": orderId,
    };
}