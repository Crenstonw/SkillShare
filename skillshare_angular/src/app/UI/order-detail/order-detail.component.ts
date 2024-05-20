import { Component, OnInit, TemplateRef, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Order, Tag } from '../../models/orders.model';
import { OrdersService } from '../../services/orders.service';
import { OrderDetail } from '../../models/orderDetail.model';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { MessageService } from '../../services/message.service';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrl: './order-detail.component.css'
})
export class OrderDetailComponent implements OnInit {
  actualMessageId: string = "";
  editOrderForm!: FormGroup;
  order: OrderDetail | undefined;
  stateColor: string = '';
  private modalService = inject(NgbModal);
  closeResult = '';

  constructor(private route: ActivatedRoute, private orderService: OrdersService, private messageService: MessageService) {
    this.route.params.subscribe(p => {
      this.orderService.GetOrderById(p['id']).subscribe(p => {
        this.order = p;
        this.setState();
      })
    })
  }

  ngOnInit(): void {
  }

  initForm() {
    this.editOrderForm = new FormGroup({
      title: new FormControl(this.order?.title),
      description: new FormControl(this.order?.description),
      price: new FormControl(this.order?.price),
      tags: new FormControl(this.tagString(this.order!.tags))
    });
  }

  tagString(tags: Tag[]): string {
    let result: string = '';
    tags.forEach(t => {
      result = result.concat(t.name + ', ');
    });
    result = result.slice(0, -2);
    return result;
  }

  tagUnString(str: string): string[] {
    let empty: string [] = []
    if(str.length != 0)
      return str.replace(/\s/g, '').split(',').reverse();
    else 
      return empty;
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

  deleteMessageModal(content: TemplateRef<any>, id: string) {
    this.actualMessageId = id;
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
      },
      (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      },
    );
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

  setState() {
    console.log(this.order?.state);
    if (this.order?.state === 'OPEN') {
      this.stateColor = 'btn-success text-white'
    } else if (this.order?.state === 'OCCUPIED') {
      this.stateColor = 'btn-warning'
    } else {
      this.stateColor = 'btn-danger text-white'
    }
  }

  changeState(status: string) {
    this.orderService.ChangeStatus(status, this.order!.id).subscribe(p => {
      window.location.reload();
    })
  }

  goBack() {
    window.history.back();
  }

  editOrder() {
    console.log(this.tagUnString(this.editOrderForm.value.tags))
    this.orderService.EditOrder(
      this.order!.id, 
      this.editOrderForm.value.title,
      this.editOrderForm.value.description,
      this.tagUnString(this.editOrderForm.value.tags),
      this.editOrderForm.value.price,
    ).subscribe(p => {
      window.location.reload();
    })
  }

  deleteMessage() {
    this.messageService.DeleteMessage(this.actualMessageId).subscribe(p => {
      window.location.reload();
    })
    this.modalService.dismissAll();
  }

  deleteOrder() {
    this.orderService.DeleteOrder(this.order!.id).subscribe(p => {
      window.history.back();
    })
  }
}
