import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:skillshare_flutter/models/responses/all_order_response.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository.dart';

part 'my_orders_event.dart';
part 'my_orders_state.dart';

class MyOrdersBloc extends Bloc<MyOrdersEvent, MyOrdersState> {
  final OrderListRepository repository;
  MyOrdersBloc(this.repository) : super(MyOrdersInitial()) {
    on<MyOrdersEvent>((event, emit) {
      on<DoMyOrdersEvent>(_doMyOrderList);
    });
  }

  void _doMyOrderList(MyOrdersEvent event, Emitter<MyOrdersState> emit) async {
    emit(MyOrdersLoading());

    try{
      final response = await repository.myOrderList();
      emit(MyOrdersSuccess(response));
    } on Exception catch (e) {
      emit(MyOrdersError(e.toString()));
    }
  }
}
