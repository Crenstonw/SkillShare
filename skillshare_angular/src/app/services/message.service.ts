import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Messages } from '../models/orderMessages.model';
import { User, Users } from '../models/users.model';
import { DirectMessage } from '../models/directChat.model';

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

  GetDirectMessagesUserWhoTalkedWith(id: string): Observable<User[]> {
    let token = localStorage.getItem('TOKEN');
    return this.http.get<User[]>(`http://localhost:8080/message/direct/users/${id}`,
      {
        headers: {
          acept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      }
    );
  }

  GetChat(userFrom: string, userTo: string): Observable<DirectMessage[]> {
    let token = localStorage.getItem('TOKEN');
    return this.http.get<DirectMessage[]>(`http://localhost:8080/message/direct/chat?userFrom=${userFrom}&userTo=${userTo}`,
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

  DeleteDirectMessage(id: string) {
    let token = localStorage.getItem('TOKEN');
    return this.http.delete(`http://localhost:8080/message/direct/${id}`,
      {
        headers: {
          acept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      }
    );
  }
}
