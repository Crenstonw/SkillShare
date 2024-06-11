import 'dart:convert';

import 'package:http/http.dart';
import 'package:skillshare_flutter/environments/local_storage.dart';
import 'package:skillshare_flutter/models/dtos/new_order_message_request.dart';
import 'package:skillshare_flutter/models/responses/new_order_message_response.dart';
import 'package:skillshare_flutter/repositories/messages/messages_repository.dart';

class MessageRepositoryImpl extends MessagesRepository {
  final Client _httpClient = Client();

  @override
  Future<NewOrderMessageResponse> newOrderMessage(NewOrderMessageRequest messageResponse) async {
    final jsonBody = json.encode(messageResponse.toJson());
    final response =
        await _httpClient.post(Uri.parse('http://10.0.2.2:8080/message/order'),
            headers: <String, String>{
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ${Localstorage.prefs.getString('token')}'
            },
            body: jsonBody);
    if (response.statusCode == 201) {
      final finalResponse = NewOrderMessageResponse.fromJson(json.decode(response.body));

      return finalResponse;
    } else {
      throw Exception('Failed to send the message');
    }
  }

  
}
