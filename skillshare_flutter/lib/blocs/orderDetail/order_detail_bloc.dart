import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:skillshare_flutter/models/responses/order_detail_response.dart';
import 'package:skillshare_flutter/models/responses/user_response.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository.dart';
import 'package:skillshare_flutter/repositories/user/user_repository.dart';

part 'order_detail_event.dart';
part 'order_detail_state.dart';

class OrderDetailBloc extends Bloc<OrderDetailEvent, OrderDetailState> {
  final OrderListRepository orderListRepository;
  final UserRepository userRepository;

  OrderDetailBloc(this.orderListRepository, this.userRepository) : super(OrderDetailInitial()) {
    on<DoOrderDetailEvent>(_doOrderDetail);
  }

  void _doOrderDetail(DoOrderDetailEvent event, Emitter<OrderDetailState> emit) async {
    emit(OrderDetailsLoading());

    try {
      final response = await orderListRepository.orderDetail(event.orderId);
      final me = await userRepository.me();
      emit(OrderDetailsSuccess(response, me));
    } on Exception catch (e) {
      print('Error en el bloc: $e');
      emit(OrderDetailsError(e.toString()));
    }
  }
}
