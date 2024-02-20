part of 'register_bloc.dart';

@immutable
sealed class RegisterEvent {}

class RegisterFetch extends RegisterEvent {
  final String email;
  final String name;
  final String surname;
  final String password;

  RegisterFetch(this.email, this.name, this.surname, this.password);
}
