part of 'home_bloc.dart';

@immutable
sealed class HomeState {}

final class HomeInitial extends HomeState {}

final class DoHomeLoading extends HomeState {}

final class DoHomeSuccess extends HomeState {
  final String userResponse;
  DoHomeSuccess(this.userResponse);
}

final class DoHomeError extends HomeState {
  final String errorMensaje;
  DoHomeError(this.errorMensaje);
}