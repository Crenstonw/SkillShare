import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Order } from '../../models/orders.model';
import { OrdersService } from '../../services/orders.service';
import { OrderDetail } from '../../models/orderDetail.model';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrl: './order-detail.component.css'
})
export class OrderDetailComponent {
  order: OrderDetail | undefined;
  constructor(private route: ActivatedRoute, private orderService: OrdersService) {
    this.route.params.subscribe(p => {
      this.orderService.GetOrderById(p['id']).subscribe(p => {
        this.order = p;
      })
    })
  }
}
