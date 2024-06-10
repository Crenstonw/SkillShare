import 'dart:convert';

import 'package:http/http.dart';
import 'package:skillshare_flutter/environments/local_storage.dart';
import 'package:skillshare_flutter/models/dtos/order_edit_request.dart';
import 'package:skillshare_flutter/models/dtos/status_request.dart';
import 'package:skillshare_flutter/models/order_edit_response.dart';
import 'package:skillshare_flutter/models/responses/all_order_response.dart';
import 'package:skillshare_flutter/models/responses/order_detail_response.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository.dart';

class OrderListRepositoryImpl extends OrderListRepository {
  final Client _httpClient = Client();

  @override
  Future<AllOrderResponse> orderList() async {
    final response = await _httpClient
        .get(Uri.parse('http://10.0.2.2:8080/order'), headers: <String, String>{
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${Localstorage.prefs.getString('token')}'
    });
    if (response.statusCode == 200) {
      final finalResponse =
          AllOrderResponse.fromJson(json.decode(response.body));
      return finalResponse;
    } else {
      throw Exception('Failed to load the list');
    }
  }

  @override
  Future<AllOrderResponse> myOrderList() async {
    final response = await _httpClient.get(
        Uri.parse('http://10.0.2.2:8080/order/myOrders'),
        headers: <String, String>{
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ${Localstorage.prefs.getString('token')}'
        });
    if (response.statusCode == 200) {
      final finalResponse =
          AllOrderResponse.fromJson(json.decode(response.body));
      return finalResponse;
    } else {
      throw Exception('Failed to load the list');
    }
  }

  @override
  Future<AllOrderResponse> orderListSearch(String title) async {
    final response = await _httpClient
        .get(Uri.parse('http://10.0.2.2:8080/order'), headers: <String, String>{
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${Localstorage.prefs.getString('token')}'
    });
    if (response.statusCode == 200) {
      final finalResponse =
          AllOrderResponse.fromJson(json.decode(response.body));
      return finalResponse;
    } else {
      throw Exception('Failed to load the list');
    }
  }

  @override
  Future<OrderDetailResponse> orderDetail(String id) async {
    final response = await _httpClient.get(
        Uri.parse('http://10.0.2.2:8080/order/${id}'),
        headers: <String, String>{
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ${Localstorage.prefs.getString('token')}'
        });
    if (response.statusCode == 200) {
      final finalResponse =
          OrderDetailResponse.fromJson(json.decode(response.body));
      return finalResponse;
    } else {
      throw Exception('Failed to load the order');
    }
  }

  @override
  Future<void> deleteOrder(String id) async {
    final response = await _httpClient.delete(
        Uri.parse('http://10.0.2.2:8080/order/${id}'),
        headers: <String, String>{
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ${Localstorage.prefs.getString('token')}'
        });
    if (response.statusCode != 204) {
      throw Exception('Failed to delete the order');
    }
  }

  @override
  Future<OrderEditResponse> orderEdit(OrderEditRequest body, String id) async {
    final jsonBody = json.encode(body.toJson());
    final response =
        await _httpClient.put(Uri.parse('http://10.0.2.2:8080/order/${id}'),
            headers: <String, String>{
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ${Localstorage.prefs.getString('token')}'
            },
            body: jsonBody);
    if (response.statusCode == 200) {
      final finalResponse =
          OrderEditResponse.fromJson(json.decode(response.body));
      return finalResponse;
    } else {
      throw Exception('Failed to login');
    }
  }
  
  @override
  Future<OrderEditResponse> changeStatus(String orderId, StatusRequest status) async{
    final jsonBody = json.encode(status.toJson());
    final response =
        await _httpClient.put(Uri.parse('http://10.0.2.2:8080/order/status/${orderId}'),
            headers: <String, String>{
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ${Localstorage.prefs.getString('token')}'
            },
            body: jsonBody);
    if (response.statusCode == 200) {
      final finalResponse =
          OrderEditResponse.fromJson(json.decode(response.body));
      return finalResponse;
    } else {
      throw Exception('Failed to login');
    }
  }
}
