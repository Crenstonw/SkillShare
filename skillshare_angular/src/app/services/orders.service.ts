import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrdersResponse } from '../models/orders.model';

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
}
