import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:skillshare_flutter/models/responses/order_detail_response.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository.dart';

part 'order_detail_event.dart';
part 'order_detail_state.dart';

class OrderDetailBloc extends Bloc<OrderDetailEvent, OrderDetailState> {
  final OrderListRepository orderListRepository;

  OrderDetailBloc(this.orderListRepository) : super(OrderDetailInitial()) {
    on<DoOrderDetailEvent>(_doOrderDetail);
  }

  void _doOrderDetail(DoOrderDetailEvent event, Emitter<OrderDetailState> emit) async {
    emit(OrderDetailsLoading());

    try {
      final response = await orderListRepository.orderDetail(event.orderId);
      emit(OrderDetailsSuccess(response));
    } on Exception catch (e) {
      print('Error en el bloc: $e');
      emit(DoOrderListError(e.toString()));
    }
  }
}
