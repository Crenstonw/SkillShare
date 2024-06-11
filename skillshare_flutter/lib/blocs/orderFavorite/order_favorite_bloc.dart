import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:skillshare_flutter/models/responses/favorite_orders_response.dart';
import 'package:skillshare_flutter/models/responses/user_response.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository.dart';
import 'package:skillshare_flutter/repositories/user/user_repository.dart';

part 'order_favorite_event.dart';
part 'order_favorite_state.dart';

class OrderFavoriteBloc extends Bloc<OrderFavoriteEvent, OrderFavoriteState> {
  final OrderListRepository orderListRepository;
  final UserRepository userRepository;
  OrderFavoriteBloc(this.orderListRepository, this.userRepository) : super(OrderFavoriteInitial()) {
    on<DoOrderFavoriteEvent>(_doOrderFavorite);
  }

  void _doOrderFavorite(DoOrderFavoriteEvent event, Emitter<OrderFavoriteState> emit) async {
    emit(OrderFavoriteLoading());

    try {
      final me = await userRepository.me();
      final response = await orderListRepository.favoriteOrders();
      emit(OrderFavoriteSuccess(response, me));
    } on Exception catch (e) {
      print('Error en el bloc: $e');
      emit(OrderFavoriteError(e.toString()));
    }
  }
}
