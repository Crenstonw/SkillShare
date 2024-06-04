import 'package:skillshare_flutter/models/dtos/new_order_dto.dart';
import 'package:skillshare_flutter/models/responses/all_order_response.dart';

abstract class NewOrderRepository {
  Future<Content> newOrder(NewOrderDto newOrderDto);
}
