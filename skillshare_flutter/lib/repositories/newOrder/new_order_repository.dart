import 'package:skillshare_flutter/models/dtos/order_edit_request.dart';
import 'package:skillshare_flutter/models/responses/order_edit_response.dart';

abstract class NewOrderRepository {
  Future<OrderEditResponse> newOrder(OrderEditRequest orderEditRequest);
}
