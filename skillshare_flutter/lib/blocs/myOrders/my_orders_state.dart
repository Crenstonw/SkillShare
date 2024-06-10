part of 'my_orders_bloc.dart';

@immutable
sealed class MyOrdersState {}

final class MyOrdersInitial extends MyOrdersState {}

final class MyOrdersLoading extends MyOrdersState {}

final class MyOrdersSuccess extends MyOrdersState {
  final AllOrderResponse orderResponse;
  final UserResponse userResponse;
  MyOrdersSuccess(this.orderResponse, this.userResponse);
}

final class MyOrdersError extends MyOrdersState {
  final String errorMensaje;
  MyOrdersError(this.errorMensaje);
}