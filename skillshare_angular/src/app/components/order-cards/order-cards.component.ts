import { Component, Input, OnInit } from '@angular/core';
import { Order } from '../../models/orders.model';

@Component({
  selector: 'app-order-cards',
  templateUrl: './order-cards.component.html',
  styleUrl: './order-cards.component.css'
})
export class OrderCardsComponent implements OnInit {

  @Input() order: Order | undefined;
  stateColor: String = '';

  ngOnInit(): void {
    this.setState();
  }

  setState() {
    if (this.order?.state === 'OPEN') {
      this.stateColor = 'bg-success text-white'
    } else if (this.order?.state === 'OCCUPIED') {
      this.stateColor = 'bg-warning'
    } else {
      this.stateColor = 'bg-danger text-white'
    }
  }

  dateScaler(dateTime: Date): string {
    const today = new Date();
    let date = dateTime.toString().split('T')[0].split('-');
    let time = dateTime.toString().split('T')[1].split(':');
    if (parseInt(date[0]) != today.getFullYear()) {
      return today.getFullYear() - parseInt(date[0]) + ` year${today.getFullYear() - parseInt(date[0]) != 1 ? 's' : ''} ago`;
    } else if (parseInt(date[1]) != (today.getMonth() + 1)) {
      return ((today.getMonth() + 1) - parseInt(date[1])) + ` month${((today.getMonth() + 1) - parseInt(date[1])) != 1 ? 's' : ''} ago`;
    } else if (parseInt(date[2]) != today.getDate()) {
      return today.getDate() - parseInt(date[2]) + ` day${today.getDate() - parseInt(date[3]) != 1 ? 's' : ''} ago`;
    } else if (parseInt(time[0]) != today.getHours()) {
      return today.getHours() - parseInt(time[0]) + ` hour${(today.getHours() - parseInt(time[0])) != 1 ? 's' : ''} ago`;
    } else if (parseInt(time[1]) != today.getMinutes()) {
      return today.getMinutes() - parseInt(time[1]) + ` minute${today.getMinutes() - parseInt(time[1]) != 1 ? 's' : ''} ago`;
    } else {
      return 'just now';
    }
  }
}
