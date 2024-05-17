import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tags } from '../models/tag.model';
import { Tag } from '../models/orders.model';

@Injectable({
  providedIn: 'root'
})
export class TagService {

  constructor(private http: HttpClient) { }

  GetTags(): Observable<Tags> {
    let token = localStorage.getItem('TOKEN');
    return this.http.get<Tags>(`http://localhost:8080/tag`,
      {
        headers: {
          acept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
  }

  PostTag(name: string): Observable<Tag> {
    let token = localStorage.getItem('TOKEN');
    return this.http.post<Tag>(`http://localhost:8080/tag`,
      {
        name
      },
      {
        headers: {
          acept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
  }

  EditTag(id: string, name: string): Observable<Tag> {
    let token = localStorage.getItem('TOKEN');
    return this.http.put<Tag>(`http://localhost:8080/tag/${id}`,
      {
        name
      },
      {
        headers: {
          acept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
  }

  DeleteTag(id: string) {
    let token = localStorage.getItem('TOKEN');
    return this.http.delete(`http://localhost:8080/tag/${id}`,
      {
        headers: {
          acept: 'application/json',
          'Authorization': `Bearer ${token}`
        }
      });
  }
}
