import { Component, OnInit } from '@angular/core';
import { User } from '../../models/users.model';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrl: './messages.component.css'
})
export class MessagesComponent implements OnInit {
  items: User[] = [];
  totalItems = 0;
  page = 0;
  pageSize = 10;

  constructor(private userService: UserService) {}

  onPageChange(page: number) {
    this.page = page;
    this.getUsers();
  }

  ngOnInit(): void {
    this.getUsers();
  }

  details(id: string) {
    window.location.pathname = `message/${id}`;
  }

  dateScaler(dateTime: Date): string {
    const today = new Date();
    let date = dateTime.toString().split('T')[0].split('-');
    let time = dateTime.toString().split('T')[1].split(':');
    if(parseInt(date[0]) != today.getFullYear()) {
      return today.getFullYear() - parseInt(date[0]) + ` year${today.getFullYear() - parseInt(date[0]) != 1 ? 's' : ''} ago`;
    }else if(parseInt(date[1]) != (today.getMonth()+1)) {
      return ((today.getMonth()+1) - parseInt(date[1])) + ` month${((today.getMonth()+1) - parseInt(date[1])) != 1 ? 's' : ''} ago`;
    } else if(parseInt(date[2]) != today.getDate()) {
      return today.getDate() - parseInt(date[3]) + ` day${today.getDate() - parseInt(date[3]) != 1 ? 's' : ''} ago`;
    } else if(parseInt(time[0]) != today.getHours()) {
      return today.getHours() - parseInt(time[0]) + ` hour${(today.getHours() - parseInt(time[0])) != 1 ? 's' : ''} ago`;
    } else if(parseInt(time[1]) != today.getMinutes()) {
      return today.getMinutes() - parseInt(time[1]) + ` minute${today.getMinutes() - parseInt(time[1]) != 1 ? 's' : ''} ago`;
    } else {
      return 'just now';
    }
  }

  getUsers() {
    this.userService.GetUsers(this.page-1).subscribe(p => {
      this.items = p.content;
      this.totalItems = p.totalElements;
    })
  }
}
