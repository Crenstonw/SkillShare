import 'dart:convert';

import 'package:http/http.dart';
import 'package:skillshare_flutter/environments/local_storage.dart';
import 'package:skillshare_flutter/models/dtos/new_order_dto.dart';
import 'package:skillshare_flutter/models/order_list_response.dart';
import 'package:skillshare_flutter/repositories/newOrder/new_order_repository.dart';

class NewOrderRepositoryImpl extends NewOrderRepository {
  final Client _httpClient = Client();

  @override
  Future<Order> newOrder(NewOrderDto newOrderDto) async {
    final jsonBody = json.encode(newOrderDto.toJson());
    final response =
        await _httpClient.post(Uri.parse('http://10.0.2.2:8080/order/new'),
            headers: <String, String>{
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ${Localstorage.prefs.getString('token')}'
            },
            body: jsonBody);
    if (response.statusCode == 201) {
      final finalResponse = Order.fromJson(response.body as Map<String, dynamic>);

      return finalResponse;
    } else {
      throw Exception('Failed to login');
    }
  }
}
