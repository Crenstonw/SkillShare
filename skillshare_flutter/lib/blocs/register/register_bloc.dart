import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:skillshare_flutter/models/dtos/register_dto.dart';
import 'package:skillshare_flutter/models/register_response.dart';
import 'package:skillshare_flutter/repositories/register/register_repository.dart';
import 'package:shared_preferences/shared_preferences.dart';

part 'register_event.dart';
part 'register_state.dart';

class RegisterBloc extends Bloc<RegisterEvent, RegisterState> {
  final RegisterRepository registerRepository;
  final Future<SharedPreferences> _prefs = SharedPreferences.getInstance();

  RegisterBloc(this.registerRepository) : super(RegisterInitial()) {
    on<RegisterFetch>(_doRegister);
  }

  void _doRegister(RegisterFetch event, Emitter<RegisterState> emit) async {
    emit(RegisterLoading());
    final SharedPreferences prefs = await _prefs;
    final String requestToken = prefs.getString('request_token') ?? '';

    try {
      final RegisterDto registerDto = RegisterDto(
        email: event.email,
        name: event.name,
        surname: event.surname,
        password: event.password,
      );
      final response = await registerRepository.register(registerDto);
      emit(RegisterFetchSuccess(response));
      return;
    } on Exception catch (e) {
      emit(RegisterFetchError(e.toString()));
    }
  }
}
