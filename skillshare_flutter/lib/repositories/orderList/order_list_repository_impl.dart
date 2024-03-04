import 'package:http/http.dart';
import 'package:skillshare_flutter/environments/local_storage.dart';
import 'package:skillshare_flutter/models/order_list_response.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository.dart';

class OrderListRepositoryImpl extends OrderListRepository {
  final Client _httpClient = Client();

  @override
  Future<List<Order>> orderList() async {
    final response = await _httpClient
        .get(Uri.parse('http://10.0.2.2:8080/order'), headers: <String, String>{
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${Localstorage.prefs.getString('token')}'
    });
    if (response.statusCode == 200) {
      final finalResponse = OrderListResponse.fromJson(response.body);
      return finalResponse.orders!;
    } else {
      throw Exception('Failed to load the list');
    }
  }
}
