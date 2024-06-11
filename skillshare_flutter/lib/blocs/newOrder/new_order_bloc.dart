import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:skillshare_flutter/models/dtos/new_order_dto.dart';
import 'package:skillshare_flutter/models/responses/all_order_response.dart';
import 'package:skillshare_flutter/repositories/newOrder/new_order_repository.dart';

part 'new_order_event.dart';
part 'new_order_state.dart';

class NewOrderBloc extends Bloc<NewOrderEvent, NewOrderState> {
  final NewOrderRepository newOrderRepository;
  NewOrderBloc(this.newOrderRepository) : super(NewOrderInitial()) {
    on<NewOrderEvent>((event, emit) {
      on<DoNewOrderEvent>(_doLogin);
    });
  }

  void _doLogin(DoNewOrderEvent event, Emitter<NewOrderState> emit) async {
    emit(DoNewOrderLoading());

    try {
      final NewOrderDto newOrderDto =
          NewOrderDto(title: event.title, description: event.description);
      final response = await newOrderRepository.newOrder(newOrderDto);
      emit(DoNewOrderSuccess(response));
      return;
    } on Exception catch (e) {
      emit(DoNewOrderError(e.toString()));
    }
  }
}
