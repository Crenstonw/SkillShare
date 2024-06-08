part of 'order_detail_bloc.dart';

@immutable
sealed class OrderDetailEvent {}

class DoOrderDetailEvent extends OrderDetailEvent {
  final String orderId;
  DoOrderDetailEvent(this.orderId);
}