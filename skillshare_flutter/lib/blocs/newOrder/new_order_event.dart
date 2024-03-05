part of 'new_order_bloc.dart';

@immutable
sealed class NewOrderEvent {}

class DoNewOrderEvent extends NewOrderEvent {
  final String title;
  final String description;
  DoNewOrderEvent(this.title, this.description);
}
