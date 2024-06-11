import 'package:skillshare_flutter/models/responses/all_order_response.dart';

abstract class HomeRepository {
  Future<String>profilePicture();
  Future<AllOrderResponse> orderList();
  Future<AllOrderResponse> searchOrderList(String title);
}
