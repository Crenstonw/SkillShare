import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:skillshare_flutter/models/responses/all_order_response.dart';
import 'package:skillshare_flutter/repositories/orderList/order_list_repository.dart';

part 'order_list_event.dart';
part 'order_list_state.dart';

class OrderListBloc extends Bloc<OrderListEvent, OrderListState> {
  final OrderListRepository orderListRepository;
  final int listType;
  int currentPage = 0;
  bool isFetching = false;

  OrderListBloc(this.orderListRepository, this.listType)
      : super(OrderListInitial()) {
    on<DoOrderListEvent>(_doOrderList);
  }

  void _doOrderList(
      DoOrderListEvent event, Emitter<OrderListState> emit) async {
    if (isFetching) return;
    isFetching = true;

    try {
      currentPage = event.loadMore ? currentPage + 1 : 0;
      final response = await orderListRepository.orderList(currentPage);
      if (event.loadMore) {
        final currentState = state;
        if (currentState is DoOrderListSuccess) {
          final updatedContent =
              List<Content>.from(currentState.orderListResponse.content)
                ..addAll(response.content);
          final updatedResponse = AllOrderResponse(
            content: updatedContent,
            pageable: response.pageable,
            totalPages: response.totalPages,
            totalElements: response.totalElements,
            last: response.last,
            number: response.number,
            size: response.size,
            numberOfElements: response.numberOfElements,
            sort: response.sort,
            first: response.first,
            empty: response.empty,
          );
          emit(DoOrderListSuccess(updatedResponse));
        }
      } else {
        emit(DoOrderListSuccess(response));
      }
    } catch (e) {
      emit(DoOrderListError(e.toString()));
    } finally {
      isFetching = false;
    }
  }
}
