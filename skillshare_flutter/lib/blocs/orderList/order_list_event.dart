part of 'order_list_bloc.dart';

@immutable
sealed class OrderListEvent {}

class DoOrderListEvent extends OrderListEvent {
  final String title;
  final bool loadMore;
  DoOrderListEvent(this.title, {this.loadMore = false});
}
