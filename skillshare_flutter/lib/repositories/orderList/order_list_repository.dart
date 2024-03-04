import 'package:skillshare_flutter/models/order_list_response.dart';

abstract class OrderListRepository {
  Future<List<Order>> orderList();
}
