import 'dart:convert';

StatusRequest statusRequestFromJson(String str) => StatusRequest.fromJson(json.decode(str));

String statusRequestToJson(StatusRequest data) => json.encode(data.toJson());

class StatusRequest {
    String status;

    StatusRequest({
        required this.status,
    });

    factory StatusRequest.fromJson(Map<String, dynamic> json) => StatusRequest(
        status: json["status"],
    );

    Map<String, dynamic> toJson() => {
        "status": status,
    };
}