import { Component, OnInit, TemplateRef, inject } from '@angular/core';
import { OrdersService } from '../../services/orders.service';
import { Order, OrdersResponse } from '../../models/orders.model';
import { FormControl, FormGroup } from '@angular/forms';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';

interface Alert {
  type: string;
  message: string;
}


@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class OrdersComponent implements OnInit {

  sortState = false;
  sortPrice = false;
  sortTag = false;
  setState = 0;
  setPrice = true;
  setTag = '';
  items: Order[] = [];
  alerts: Alert[] = [];
  newOrderForm!: FormGroup;
  totalItems = 0;
  page = 0;
  pageSize = 10;
  private modalService = inject(NgbModal);
  closeResult = '';

  constructor(private orderService: OrdersService) { }

  ngOnInit(): void {
    this.getOrders();
  }

  onPageChange(page: number) {
    this.page = page;
    if(this.sortPrice)
      this.getOrdersPrice(this.setPrice);
    else if(this.sortState)
      this.getOrdersState
    else if(this.sortTag)
      this.getOrdersTag
    else
      this.getOrders();
  }

  close(alert: Alert) {
    this.alerts.splice(this.alerts.indexOf(alert), 1);
  }

  reset() {
    this.alerts = [];
  }

  initForm() {
    this.newOrderForm = new FormGroup({
      title: new FormControl(),
      description: new FormControl(),
      price: new FormControl(),
      tags: new FormControl()
    });
  }

  tagUnString(str: string): string[] {
    let empty: string[] = []
    if (str.length != 0)
      return str.replace(/\s/g, '').split(',').reverse();
    else
      return empty;
  }

  orderModal(content: TemplateRef<any>) {
    this.initForm();
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title', size: 'xl' }).result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
      },
      (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      },
    );
  }

  private getDismissReason(reason: any): string {
    switch (reason) {
      case ModalDismissReasons.ESC:
        return 'by pressing ESC';
      case ModalDismissReasons.BACKDROP_CLICK:
        return 'by clicking on a backdrop';
      default:
        return `with: ${reason}`;
    }
  }

  getOrders(): void {
    if(this.sortPrice || this.sortState || this.sortTag)
      this.page = 0;
    this.sortState = false;
    this.sortPrice = false;
    this.sortTag = false;
    this.orderService.GetOrders(this.page - 1).subscribe(p => {
      this.items = p.content;
      this.totalItems = p.totalElements;
    });
  }

  getOrdersState(state: number): void {
    if(!this.sortState)
      this.page = 0;
    this.sortState = true;
    this.setState = state;
    this.orderService.GetOrdersState(this.page - 1, state).subscribe(p => {
      this.items = p.content;
      this.totalItems = p.totalElements;
    });
  }

  getOrdersPrice(price: boolean): void {
    if(!this.sortPrice)
      this.page = 0;
    this.sortPrice = true;
    this.setPrice = price;
    this.orderService.GetOrdersPrice(this.page - 1, price).subscribe(p => {
      this.items = p.content;
      this.totalItems = p.totalElements;
    });
  }

  getOrdersTag(tag: string): void {
    if(!this.sortTag)
      this.page = 0;
    this.sortTag = true;
    this.setTag = tag;
    this.orderService.GetOrdersTag(this.page - 1, tag).subscribe({
      next: (p: OrdersResponse) => {
        this.items = p.content;
        this.totalItems = p.totalElements;
      },
      error: (err) => {
        this.reset();
        this.alerts.push({ type: 'danger', message: `${err.error.message}` });
      }
    });
  }

  details(id: string) {
    window.location.pathname = `order/${id}`;
  }

  newOrder() {
    this.orderService.NewOrder(
      this.newOrderForm.value.title,
      this.newOrderForm.value.description,
      this.tagUnString(this.newOrderForm.value.tags),
      this.newOrderForm.value.price
    ).subscribe(p => {
      this.getOrders();
    })
  }

}
