import { Component, OnInit, TemplateRef, inject } from '@angular/core';
import { OrdersService } from '../../services/orders.service';
import { Order } from '../../models/orders.model';
import { FormControl, FormGroup } from '@angular/forms';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class OrdersComponent implements OnInit {

  items: Order[] = [];
  newOrderForm!: FormGroup;
  totalItems = 0;
  page = 0;
  pageSize = 10;
  private modalService = inject(NgbModal);
  closeResult = '';

  constructor (private orderService: OrdersService) {}

  ngOnInit(): void {
    this.getOrders();
  }

  onPageChange(page: number) {
    this.page = page;
    this.getOrders();
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
    let empty: string [] = []
    if(str.length != 0)
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
    this.orderService.GetOrders(this.page-1).subscribe(p => {
     this.items = p.content;
     this.totalItems = p.totalElements;
    })
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
      window.location.reload();
    })
  }

}
