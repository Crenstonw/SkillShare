import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:skillshare_flutter/models/dtos/login_request.dart';
import 'package:skillshare_flutter/models/responses/login_response.dart';
import 'package:skillshare_flutter/repositories/login/login_repository.dart';

part 'login_event.dart';
part 'login_state.dart';

class LoginBloc extends Bloc<LoginEvent, LoginState> {
  final LoginRepository loginRepository;
  //final Future<SharedPreferences> _prefs = SharedPreferences.getInstance();

  LoginBloc(this.loginRepository) : super(LoginInitial()) {
    on<DoLoginEvent>(_doLogin);
  }

  void _doLogin(DoLoginEvent event, Emitter<LoginState> emit) async {
    emit(DoLoginLoading());

    try {
      final LoginRequest loginRequest =
          LoginRequest(email: event.email, password: event.password);
      final response = await loginRepository.login(loginRequest);
      emit(DoLoginSuccess(response));
      return;
    } on Exception catch (e) {
      emit(DoLoginError(e.toString()));
    }
  }
}
