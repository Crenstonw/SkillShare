import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Messages } from '../models/orderMessages.model';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private http: HttpClient) { }

  GetOrderMessagesByUser(id: string): Observable<Messages> {
    let token = localStorage.getItem('TOKEN');
    return this.http.get<Messages>(`http://localhost:8080/message/order/user/${id}`,
      {
        headers: {
          acept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      }
    );
  }

  DeleteMessage(id: string) {
    let token = localStorage.getItem('TOKEN');
    return this.http.delete(`http://localhost:8080/message/order/${id}`,
      {
        headers: {
          acept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      }
    );
  }
}
