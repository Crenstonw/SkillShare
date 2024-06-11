part of 'order_detail_bloc.dart';

@immutable
sealed class OrderDetailState {}

final class OrderDetailInitial extends OrderDetailState {}

final class OrderDetailsLoading extends OrderDetailState {}

final class  OrderDetailsSuccess extends OrderDetailState {
  final OrderDetailResponse orderDetailResponse;
  final UserResponse me;
   OrderDetailsSuccess(this.orderDetailResponse, this.me);
}

final class OrderDetailsError extends OrderDetailState {
  final String errorMensaje;
  OrderDetailsError(this.errorMensaje);
}
