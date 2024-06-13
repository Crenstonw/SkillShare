part of 'order_list_bloc.dart';

@immutable
sealed class OrderListState {}

final class OrderListInitial extends OrderListState {}

final class DoOrderListLoading extends OrderListState {
  
}

final class DoOrderListSuccess extends OrderListState {
  final AllOrderResponse orderListResponse;
  DoOrderListSuccess(this.orderListResponse);
}

final class DoOrderListError extends OrderListState {
  final String errorMensaje;
  DoOrderListError(this.errorMensaje);
}
