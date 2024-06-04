part of 'new_order_bloc.dart';

@immutable
sealed class NewOrderState {}

final class NewOrderInitial extends NewOrderState {}

final class DoNewOrderLoading extends NewOrderState {}

final class DoNewOrderSuccess extends NewOrderState {
  final Content newOrder;
  DoNewOrderSuccess(this.newOrder);
}

final class DoNewOrderError extends NewOrderState {
  final String errorMensaje;
  DoNewOrderError(this.errorMensaje);
}
