import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Order, OrdersResponse } from '../models/orders.model';
import { OrderDetail } from '../models/orderDetail.model';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  constructor(private http: HttpClient) { }

  GetOrders(page: number): Observable<OrdersResponse> {
    let token = localStorage.getItem('TOKEN');
    return this.http.get<OrdersResponse>(`http://localhost:8080/order?page=${page}`,
      {
        headers: {
          acept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      }
    );
  }

  GetOrderById(id: string): Observable<OrderDetail> {
    let token = localStorage.getItem('TOKEN');
    return this.http.get<OrderDetail>(`http://localhost:8080/order/${id}`,
      {
        headers: {
          acept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      }
    );
  }

  NewOrder(title: string, description: string, tags: string[], price: number): Observable<Order> {
    let token = localStorage.getItem('TOKEN');
    return this.http.post<Order>(`http://localhost:8080/order`,
      {
        'title': `${title}`,
        'description': `${description}`,
        'tags': tags,
        'price': `${price}`,
      },
      {
        headers: {
          acept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      }
    );
  }

  ChangeStatus(status: string, id: string): Observable<Order> {
    let token = localStorage.getItem('TOKEN');
    return this.http.put<Order>(`http://localhost:8080/order/status/${id}`,
      {
        'status': `${status}`
      },
      {
        headers: {
          acept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      }
    );
  }

  EditOrder(id: string, title: string, description: string, tags: string[], price: number): Observable<OrderDetail> {
    let token = localStorage.getItem('TOKEN');
    return this.http.put<OrderDetail>(`http://localhost:8080/order/${id}`,
      {
        'title': `${title}`,
        'description': `${description}`,
        'tags': tags,
        'price': `${price}`,
      },
      {
        headers: {
          acept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      }
    );
  }

  DeleteOrder(id: string) {
    let token = localStorage.getItem('TOKEN');
    return this.http.delete(`http://localhost:8080/order/${id}`,
      {
        headers: {
          acept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      }
    );
  }
}
