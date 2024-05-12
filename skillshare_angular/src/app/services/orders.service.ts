import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrdersResponse } from '../models/orders.model';
import { OrderDetail } from '../models/orderDetail.model';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  constructor(private http: HttpClient) { }

  GetOrders(): Observable<OrdersResponse> {
    let token = localStorage.getItem('TOKEN');
    return this.http.get<OrdersResponse>(`http://localhost:8080/order`,
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
}
