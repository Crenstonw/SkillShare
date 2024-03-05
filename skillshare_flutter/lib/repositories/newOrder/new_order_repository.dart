import 'package:skillshare_flutter/models/dtos/new_order_dto.dart';
import 'package:skillshare_flutter/models/order_list_response.dart';

abstract class NewOrderRepository {
  Future<Order> newOrder(NewOrderDto newOrderDto);
}
