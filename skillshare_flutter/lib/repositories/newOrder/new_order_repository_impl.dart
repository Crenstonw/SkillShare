import 'dart:convert';

import 'package:http/http.dart';
import 'package:skillshare_flutter/environments/local_storage.dart';
import 'package:skillshare_flutter/models/dtos/order_edit_request.dart';
import 'package:skillshare_flutter/models/responses/order_edit_response.dart';
import 'package:skillshare_flutter/repositories/newOrder/new_order_repository.dart';

class NewOrderRepositoryImpl extends NewOrderRepository {
  final Client _httpClient = Client();

  @override
  Future<OrderEditResponse> newOrder(OrderEditRequest orderEditRequest) async {
    final jsonBody = json.encode(orderEditRequest.toJson());
    final response =
        await _httpClient.post(Uri.parse('http://10.0.2.2:8080/order'),
            headers: <String, String>{
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ${Localstorage.prefs.getString('token')}'
            },
            body: jsonBody);
    if (response.statusCode == 201) {
      final finalResponse = OrderEditResponse.fromJson(json.decode(response.body));

      return finalResponse;
    } else {
      throw Exception('Failed to create a new order');
    }
  }
}
