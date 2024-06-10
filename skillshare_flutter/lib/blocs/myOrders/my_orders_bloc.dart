import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:skillshare_flutter/models/responses/all_order_response.dart';
import 'package:skillshare_flutter/models/responses/user_response.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository.dart';
import 'package:skillshare_flutter/repositories/user/user_repository.dart';

part 'my_orders_event.dart';
part 'my_orders_state.dart';

class MyOrdersBloc extends Bloc<DoMyOrdersEvent, MyOrdersState> {
  final OrderListRepository repository;
  final UserRepository userRepository;
  MyOrdersBloc(this.repository, this.userRepository) : super(MyOrdersInitial()) {
    on<DoMyOrdersEvent>(_doMyOrderList);
  }

  void _doMyOrderList(DoMyOrdersEvent event, Emitter<MyOrdersState> emit) async {
    emit(MyOrdersLoading());

    try{
      final orderResponse = await repository.myOrderList();
      final me = await userRepository.me();
      emit(MyOrdersSuccess(orderResponse, me));
      return;
    } on Exception catch (e) {
      emit(MyOrdersError(e.toString()));
    }
  }
}
