part of 'order_detail_bloc.dart';

@immutable
sealed class OrderDetailState {}

final class OrderDetailInitial extends OrderDetailState {}

final class OrderDetailsLoading extends OrderDetailState {}

final class  OrderDetailsSuccess extends OrderDetailState {
  final OrderDetailResponse orderDetailResponse;
   OrderDetailsSuccess(this.orderDetailResponse);
}

final class DoOrderListError extends OrderDetailState {
  final String errorMensaje;
  DoOrderListError(this.errorMensaje);
}
