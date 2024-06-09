part of 'my_orders_bloc.dart';

@immutable
sealed class MyOrdersState {}

final class MyOrdersInitial extends MyOrdersState {}

final class MyOrdersLoading extends MyOrdersState {}

final class MyOrdersSuccess extends MyOrdersState {
  final AllOrderResponse response;
  MyOrdersSuccess(this.response);
}

final class MyOrdersError extends MyOrdersState {
  final String errorMensaje;
  MyOrdersError(this.errorMensaje);
}