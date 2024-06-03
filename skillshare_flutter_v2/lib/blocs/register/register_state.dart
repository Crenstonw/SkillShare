part of 'register_bloc.dart';

@immutable
sealed class RegisterState {}

final class RegisterInitial extends RegisterState {}

final class RegisterLoading extends RegisterState {}

final class RegisterFetchSuccess extends RegisterState {
  final RegisterResponse registerResponse;
  RegisterFetchSuccess(this.registerResponse);
}

final class RegisterFetchError extends RegisterState {
  final String errorMessage;
  RegisterFetchError(this.errorMessage);
}
