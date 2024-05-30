import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Users } from '../models/users.model';
import { Observable } from 'rxjs';
import { UserDetail } from '../models/userDetail.model';
import { User } from '../models/orders.model';

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
          Accept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
  }

  GetUser(id: string): Observable<UserDetail> {
    let token = localStorage.getItem('TOKEN');
    return this.http.get<UserDetail>(`http://localhost:8080/user/${id}`,
      {
        headers: {
          Accept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
  }

  GetMe(): Observable<User> {
    let token = localStorage.getItem('TOKEN');
    return this.http.get<User>(`http://localhost:8080/user/me`,
      {
        headers: {
          Accept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
  }

  NewUser(name: string, surname: string, username: string, password: string, email: string): Observable<User> {
    let token = localStorage.getItem('TOKEN');
    return this.http.post<User>(`http://localhost:8080/user`,
      {
        email: email,
        username: username,
        name: name,
        surname: surname,
        password: password
      },
      {
        headers: {
          Accept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
  }

  EditUser(id: string, name: string, surname: string, username: string, profilePicture: string, email: string): Observable<UserDetail>{
    let token = localStorage.getItem('TOKEN');
    return this.http.put<UserDetail>(`http://localhost:8080/user/${id}`,
      {
        name: name,
        surname: surname,
        profilePicture: profilePicture,
        username: username,
        email: email
      },
      {
        headers: {
          Accept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
  }

  DeleteUser(id: string) {
    let token = localStorage.getItem('TOKEN');
    return this.http.delete(`http://localhost:8080/user/${id}`,
      {
        headers: {
          Accept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
  }

  BanUser(id: string): Observable<UserDetail> {
    let token = localStorage.getItem('TOKEN');
    return this.http.put<UserDetail>(`http://localhost:8080/user/ban/${id}`,
      {},
      {
        headers: {
          Accept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
  }

  ChangePrivileges(id: string): Observable<UserDetail> {
    let token = localStorage.getItem('TOKEN');
    return this.http.put<UserDetail>(`http://localhost:8080/user/privileges/${id}`,
      {},
      {
        headers: {
          Accept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
  }
}
