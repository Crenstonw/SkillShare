import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Users } from '../models/users.model';
import { Observable } from 'rxjs';
import { UserDetail } from '../models/userDetail.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  GetUsers(): Observable<Users> {
    let token = localStorage.getItem('TOKEN');
    return this.http.get<Users>(`http://localhost:8080/user`,
      {
        headers: {
          acept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
  }

  GetUser(id: string): Observable<UserDetail> {
    let token = localStorage.getItem('TOKEN');
    return this.http.get<UserDetail>(`http://localhost:8080/user/${id}`,
      {
        headers: {
          acept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
  }
}
