
import 'package:skillshare_flutter/models/dtos/order_edit_request.dart';
import 'package:skillshare_flutter/models/dtos/status_request.dart';
import 'package:skillshare_flutter/models/responses/favorite_orders_response.dart';
import 'package:skillshare_flutter/models/responses/favorite_response.dart';
import 'package:skillshare_flutter/models/responses/order_edit_response.dart';
import 'package:skillshare_flutter/models/responses/all_order_response.dart';
import 'package:skillshare_flutter/models/responses/order_detail_response.dart';

abstract class OrderListRepository {
  Future<AllOrderResponse> orderList(int page);
  Future<AllOrderResponse> myOrderList();
  Future<AllOrderResponse> orderListSearch(String title);
  Future<OrderDetailResponse> orderDetail(String id);
  Future<OrderEditResponse> orderEdit(OrderEditRequest body, String id);
  Future<OrderEditResponse> changeStatus(String orderId, StatusRequest status);
  Future<void> deleteOrder(String id);
  Future<List<FavoriteOrdersResponse>> favoriteOrders();
  Future<Map<FavoriteResponse, String>> addFavorite(String id);
  Future<Map<FavoriteResponse, String>> removeFavorite(String id);
}
