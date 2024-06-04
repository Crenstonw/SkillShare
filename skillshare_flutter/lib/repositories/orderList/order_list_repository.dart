
import 'package:skillshare_flutter/models/responses/all_order_response.dart';

abstract class OrderListRepository {
  Future<AllOrderResponse> orderList();
  Future<AllOrderResponse> orderListSearch(String title);
}
