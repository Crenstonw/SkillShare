import { Component, OnInit } from '@angular/core';
import { User } from '../../models/orders.model';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent implements OnInit{
  items: User[] = [];

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.getUsers();
  }

  details(id: string) {
    window.location.pathname = `user/${id}`;
  }

  isAdmin(role: string) {
    if(role === '[ADMIN]')
      return true;
    else if(role === '[USER]')
      return false;
    else
      return false;
  }

  dateScaler(dateTime: Date): string {
    const today = new Date();
    let date = dateTime.toString().split('T')[0].split('-');
    let time = dateTime.toString().split('T')[1].split(':');
    console.log((today.getMonth()+1) + ' ' + parseInt(date[1]));
    if(parseInt(date[0]) != today.getFullYear()) {
      return today.getFullYear() - parseInt(date[0]) + ' year/s ago';
    }else if(parseInt(date[1]) != (today.getMonth()+1)) {
      return ((today.getMonth()+1) - parseInt(date[1])) + ' month/s ago';
    } else if(parseInt(date[2]) != today.getDate()) {
      return today.getDate() - parseInt(date[3]) + ' day/s ago';
    } else if(parseInt(time[0]) != today.getHours()) {
      return today.getHours() - parseInt(time[0]) + ' hour/s ago';
    } else if(parseInt(time[1]) != today.getMinutes()) {
      return today.getMinutes() - parseInt(time[1]) + ' minute/s ago';
    } else {
      return 'just now';
    }
  }

  getUsers() {
    this.userService.GetUsers().subscribe(p => {
      this.items = p.content;
    })
  }

}
