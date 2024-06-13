part of 'my_profile_bloc.dart';

@immutable
sealed class MyProfileState {}

final class MyProfileInitial extends MyProfileState {}

final class MyProfileLoading extends MyProfileState {}

final class MyProfileSuccess extends MyProfileState {
  final UserResponse me;
  MyProfileSuccess(this.me);
}

final class MyProfileError extends MyProfileState {
  final String errorMessaje;
  MyProfileError(this.errorMessaje);
}