import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:skillshare_flutter_v2/models/dto/register_request.dart';
import 'package:skillshare_flutter_v2/models/register_response.dart';
import 'package:skillshare_flutter_v2/repositories/register/register_respository.dart';

part 'register_event.dart';
part 'register_state.dart';

class RegisterBloc extends Bloc<RegisterEvent, RegisterState> {
  final RegisterRepository registerRepository;

  RegisterBloc(this.registerRepository) : super(RegisterInitial()) {
    on<RegisterFetch>(_doRegister);
  }

  void _doRegister(RegisterFetch event, Emitter<RegisterState> emit) async {
    emit(RegisterLoading());

    try {
      final RegisterRequest registerRequest = RegisterRequest(
        email: event.email,
        username: event.username,
        name: event.name,
        surname: event.surname,
        password: event.password,
      );
      final response = await registerRepository.register(registerRequest);
      emit(RegisterFetchSuccess(response));
      return;
    } on Exception catch (e) {
      emit(RegisterFetchError(e.toString()));
    }
  }
}
