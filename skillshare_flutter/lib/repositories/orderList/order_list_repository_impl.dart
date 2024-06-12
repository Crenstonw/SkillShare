import 'dart:convert';

import 'package:http/http.dart';
import 'package:skillshare_flutter/environments/local_storage.dart';
import 'package:skillshare_flutter/models/dtos/order_edit_request.dart';
import 'package:skillshare_flutter/models/dtos/status_request.dart';
import 'package:skillshare_flutter/models/responses/favorite_orders_response.dart';
import 'package:skillshare_flutter/models/responses/favorite_response.dart';
import 'package:skillshare_flutter/models/responses/order_edit_response.dart';
import 'package:skillshare_flutter/models/responses/all_order_response.dart';
import 'package:skillshare_flutter/models/responses/order_detail_response.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository.dart';

class OrderListRepositoryImpl extends OrderListRepository {
  final Client _httpClient = Client();

  @override
  Future<AllOrderResponse> orderList(int page) async {
    final response = await _httpClient
        .get(Uri.parse('http://10.0.2.2:8080/order?page=$page'), headers: <String, String>{
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
        Uri.parse('http://10.0.2.2:8080/order/$id'),
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
        Uri.parse('http://10.0.2.2:8080/order/$id'),
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
        await _httpClient.put(Uri.parse('http://10.0.2.2:8080/order/$id'),
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
        await _httpClient.put(Uri.parse('http://10.0.2.2:8080/order/status/$orderId'),
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
      throw Exception('Failed to change status');
    }
  }

  @override
  Future<Map<FavoriteResponse, String>> addFavorite(String id) async{
    final response =
        await _httpClient.put(Uri.parse('http://10.0.2.2:8080/user/favorite/$id'),
            headers: <String, String>{
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ${Localstorage.prefs.getString('token')}'
            });
    if (response.statusCode == 200) {
      final finalResponse =
          FavoriteResponse.fromJson(json.decode(response.body));
      return {finalResponse: 'added successfuly'};
    } else if(response.statusCode == 409) {
      return removeFavorite(id);
    } else{
      throw Exception('Failed to add favorite');
    }
  }

  @override
  Future<List<FavoriteOrdersResponse>> favoriteOrders() async {
  final response = await _httpClient.get(
    Uri.parse('http://10.0.2.2:8080/user/favorite'),
    headers: <String, String>{
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${Localstorage.prefs.getString('token')}'
    },
  );

  if (response.statusCode == 200) {
    return favoriteOrdersResponseFromJson(response.body);
  } else {
    throw Exception('Failed to load the orders');
  }
}

  @override
  Future<Map<FavoriteResponse, String>> removeFavorite(String id) async{
    final response =
        await _httpClient.put(Uri.parse('http://10.0.2.2:8080/user/unfavorite/$id'),
            headers: <String, String>{
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ${Localstorage.prefs.getString('token')}'
            });
    if (response.statusCode == 200) {
      final finalResponse =
          FavoriteResponse.fromJson(json.decode(response.body));
      return {finalResponse: 'removed successfuly'};
    } else{
      throw Exception('Failed to remove favorite');
    }
  }
}
