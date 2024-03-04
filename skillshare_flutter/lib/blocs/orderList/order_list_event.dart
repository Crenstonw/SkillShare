part of 'order_list_bloc.dart';

@immutable
sealed class OrderListEvent {}

class DoOrderListEvent extends OrderListEvent {
  DoOrderListEvent();
}
