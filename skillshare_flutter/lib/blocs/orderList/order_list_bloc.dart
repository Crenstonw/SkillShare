import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:skillshare_flutter/models/responses/all_order_response.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository.dart';

part 'order_list_event.dart';
part 'order_list_state.dart';

class OrderListBloc extends Bloc<OrderListEvent, OrderListState> {
  final OrderListRepository orderListRepository;
  final int listType;

  OrderListBloc(this.orderListRepository, this.listType) : super(OrderListInitial()) {
    on<DoOrderListEvent>(_doOrderList);
  }

  void _doOrderList(
      DoOrderListEvent event, Emitter<OrderListState> emit) async {
    emit(DoOrderListLoading());

    try {
      final response;
      if(listType == 0) {
        response = await orderListRepository.orderList();
      } else if(listType == 1) {
        response = await orderListRepository.myOrderList();
      } else {
        response = await orderListRepository.orderList();
      }
      emit(DoOrderListSuccess(response));
      return;
    } on Exception catch (e) {
      print('Error en el bloc: $e');
      emit(DoOrderListError(e.toString()));
    }
  }
}
