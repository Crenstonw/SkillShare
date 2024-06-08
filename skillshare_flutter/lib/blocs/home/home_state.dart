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

final class DoOrderListSuccess extends HomeState {
  final AllOrderResponse orderListResponse;
  DoOrderListSuccess(this.orderListResponse);
}

final class DoOrderListError extends HomeState {
  final String errorMensaje;
  DoOrderListError(this.errorMensaje);
}

/*final class DoSearchOrderListSuccess extends HomeState {
  final List<Order> searchOrderListResponse;
  DoSearchOrderListSuccess(this.searchOrderListResponse);
}

final class DoSearchOrderListError extends HomeState {
  final String errorMensaje;
  DoSearchOrderListError(this.errorMensaje);
}*/