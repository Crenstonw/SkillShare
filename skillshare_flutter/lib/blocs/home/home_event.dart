part of 'home_bloc.dart';

@immutable
sealed class HomeEvent {}

class DoHomeEvent extends HomeEvent {
  DoHomeEvent();
}

class DoOrderListEvent extends HomeEvent {
  DoOrderListEvent();
}

/*class DoSearchOrderListEvent extends HomeEvent {
  final String title;
  DoSearchOrderListEvent(this.title);
}*/