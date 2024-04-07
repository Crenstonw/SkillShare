import 'package:skillshare_flutter/models/order_list_response.dart';

abstract class HomeRepository {
  Future<String>profilePicture();
  Future<List<Order>> orderList();
  Future<List<Order>> searchOrderList(String title);
}
