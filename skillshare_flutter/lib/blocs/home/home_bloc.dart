import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:skillshare_flutter/models/responses/all_order_response.dart';
import 'package:skillshare_flutter/repositories/home/home_repository.dart';

part 'home_event.dart';
part 'home_state.dart';

class HomeBloc extends Bloc<HomeEvent, HomeState> {
  final HomeRepository homeRepository;

  HomeBloc(this.homeRepository) : super(HomeInitial()) {
    on<DoHomeEvent>(_doHome);
    /*on<DoOrderListEvent>(_doOrderList);
    on<DoSearchOrderListEvent>(_doSearchOrderList as EventHandler<DoSearchOrderListEvent, HomeState>);*/
  }

  void _doHome(DoHomeEvent event, Emitter<HomeState> emit) async {
    emit(DoHomeLoading());

    try {
      final response = await homeRepository.profilePicture();
      emit(DoHomeSuccess(response));
      return;
    } on Exception catch (e) {
      print('Error en el bloc: $e');
      emit(DoHomeError(e.toString()));
    }
  }

  void _doOrderList(
      DoOrderListEvent event, Emitter<HomeState> emit) async {
    emit(DoHomeLoading());

    try {
      final response = await homeRepository.orderList();
      emit(DoOrderListSuccess(response));
      return;
    } on Exception catch (e) {
      print('Error en el bloc: $e');
      emit(DoOrderListError(e.toString()));
    }
  }

  /*void _doSearchOrderList(
      DoOrderListEvent event, Emitter<HomeState> emit) async {
    emit(DoHomeLoading());
    try {
      final response = await homeRepository.searchOrderList(event.toString());
      emit(DoSearchOrderListSuccess(response));
      return;
    } on Exception catch (e) {
      print('Error en el bloc: $e');
      emit(DoSearchOrderListError(e.toString()));
    }
  }*/
}
