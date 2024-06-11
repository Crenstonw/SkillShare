import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:skillshare_flutter/models/responses/user_response.dart';
import 'package:skillshare_flutter/repositories/user/user_repository.dart';

part 'my_profile_event.dart';
part 'my_profile_state.dart';

class MyProfileBloc extends Bloc<MyProfileEvent, MyProfileState> {
  final UserRepository userRepository;

  MyProfileBloc(this.userRepository) : super(MyProfileInitial()) {
    on<DoMyProfileEvent>(_myProfile);
  }

  void _myProfile(DoMyProfileEvent event, Emitter<MyProfileState> emit) async {
    emit(MyProfileLoading());

    try {
      final response = await userRepository.me();
      emit(MyProfileSuccess(response));
    } on Exception catch (e) {
      print('Error en el bloc: $e');
      emit(MyProfileError(e.toString()));
    }
  }
}
