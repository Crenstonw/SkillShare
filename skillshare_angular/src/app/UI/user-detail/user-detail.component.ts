import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../services/user.service';
import { UserDetail } from '../../models/userDetail.model';
import { Order } from '../../models/orders.model';

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
        this.isBanned(p.enabled);
      })
    })
  }

  goBack() {
    window.history.back();
  }

  dateScaler(dateTime: Date): string {
    const today = new Date();
    let date = dateTime.toString().split('T')[0].split('-');
    let time = dateTime.toString().split('T')[1].split(':');
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

  isBanned(enabled: boolean) {
    if(enabled)
      return 'hide';
    else {
      return '';
    }
  }

  moveToOrder(id: string) {
    window.location.pathname = `order/${id}`
  }

  setState(order: Order) {
    if(order.state === 'OPEN') {
      return 'bg-success text-white'
    } else if(order.state === 'OCCUPIED') {
      return 'bg-warning'
    } else {
      return 'bg-danger text-white'
    }
  }

  banUser(id: string) {
    this.userService.BanUser(id).subscribe({
      next: (p: UserDetail) => {
        this.user = p;
      this.isBanned(p.enabled);
      console.log(p.role);
      },
      error: (err) => {
        console.log(err.error.message);
      }
    });
  }
}
