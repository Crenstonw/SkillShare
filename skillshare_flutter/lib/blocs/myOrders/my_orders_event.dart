part of 'my_orders_bloc.dart';

@immutable
sealed class MyOrdersEvent {}

class DoMyOrdersEvent extends MyOrdersEvent {}