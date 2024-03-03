part of 'home_bloc.dart';

@immutable
sealed class HomeEvent {}

class DoHomeEvent extends HomeEvent {
  DoHomeEvent();
}