part of 'register_bloc.dart';

@immutable
sealed class RegisterEvent {}

class RegisterFetch extends RegisterEvent {
  final String email;
  final String username;
  final String name;
  final String surname;
  final String password;

  RegisterFetch(this.email, this.username, this.name, this.surname, this.password);
}
