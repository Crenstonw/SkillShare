part of 'order_favorite_bloc.dart';

@immutable
sealed class OrderFavoriteEvent {}

class DoOrderFavoriteEvent extends OrderFavoriteEvent {}