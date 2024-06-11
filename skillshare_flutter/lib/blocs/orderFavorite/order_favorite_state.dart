part of 'order_favorite_bloc.dart';

@immutable
sealed class OrderFavoriteState {}

final class OrderFavoriteInitial extends OrderFavoriteState {}

final class OrderFavoriteLoading extends OrderFavoriteState {}

final class  OrderFavoriteSuccess extends OrderFavoriteState {
  final List<FavoriteOrdersResponse> favorites;
  final UserResponse me;
   OrderFavoriteSuccess(this.favorites, this.me);
}

final class OrderFavoriteError extends OrderFavoriteState {
  final String errorMensaje;
  OrderFavoriteError(this.errorMensaje);
}