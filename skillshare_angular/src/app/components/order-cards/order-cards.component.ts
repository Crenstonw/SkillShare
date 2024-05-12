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
    if(this.order?.state === 'OPEN') {
      this.stateColor = 'bg-success text-white'
    } else if(this.order?.state === 'OCCUPIED') {
      this.stateColor = 'bg-warning'
    } else {
      this.stateColor = 'bg-danger text-white'
    }
  }
}
