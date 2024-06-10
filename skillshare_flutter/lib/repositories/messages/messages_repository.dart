
import 'package:skillshare_flutter/models/dtos/new_order_message_request.dart';
import 'package:skillshare_flutter/models/responses/new_order_message_response.dart';

abstract class MessagesRepository {
  Future<NewOrderMessageResponse> newOrderMessage(NewOrderMessageRequest messageResponse);
}