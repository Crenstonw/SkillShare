import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private http: HttpClient) { }

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
