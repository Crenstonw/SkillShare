
import 'package:skillshare_flutter/models/responses/all_order_response.dart';
import 'package:skillshare_flutter/models/responses/order_detail_response.dart';

abstract class OrderListRepository {
  Future<AllOrderResponse> orderList();
  Future<AllOrderResponse> orderListSearch(String title);
  Future<OrderDetailResponse> orderDetail(String id);
}
