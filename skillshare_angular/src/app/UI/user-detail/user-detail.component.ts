import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../services/user.service';
import { UserDetail } from '../../models/userDetail.model';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrl: './user-detail.component.css'
})
export class UserDetailComponent {

  user: UserDetail | undefined;

  constructor(private route: ActivatedRoute, private userService: UserService) {
    this.route.params.subscribe(p => {
      this.userService.GetUser(p['id']).subscribe(p => {
        this.user = p;
        console.log(p.favoriteOrders[0].title);
      })
    })
  }
}
