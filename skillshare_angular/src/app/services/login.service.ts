import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginResponse } from '../models/login.model';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  LoginResponseUser(email: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`http://localhost:8080/auth/login`,
      {
        "email": `${email}`,
        "password": `${password}`
      }
    );
  }
}
