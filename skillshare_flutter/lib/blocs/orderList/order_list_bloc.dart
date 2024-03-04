import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:skillshare_flutter/models/order_list_response.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository.dart';

part 'order_list_event.dart';
part 'order_list_state.dart';

class OrderListBloc extends Bloc<OrderListEvent, OrderListState> {
  final OrderListRepository orderListRepository;

  OrderListBloc(this.orderListRepository) : super(OrderListInitial()) {
    on<DoOrderListEvent>(_doOrderList);
  }

  void _doOrderList(
      DoOrderListEvent event, Emitter<OrderListState> emit) async {
    emit(DoOrderListLoading());

    try {
      final response = await orderListRepository.orderList();
      emit(DoOrderListSuccess(response));
      return;
    } on Exception catch (e) {
      print('Error en el bloc: $e');
      emit(DoOrderListError(e.toString()));
    }
  }
}
