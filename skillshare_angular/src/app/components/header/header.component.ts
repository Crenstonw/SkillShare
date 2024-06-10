import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../models/orders.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {
  user: User | undefined;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.getMe();
  }

  signOut() {
    localStorage.clear();
    window.location.href = '/auth';
  }

  myProfile() {
    window.location.pathname = `user/${this.user!.id}`
  }

  teapot() {
    window.location.pathname = `teapot`
  }

  getMe() {
    this.userService.GetMe().subscribe(p => {
      this.user = p;
    })
  }
}
