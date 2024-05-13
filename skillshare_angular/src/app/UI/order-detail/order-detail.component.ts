import { Component, OnInit, TemplateRef, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Order } from '../../models/orders.model';
import { OrdersService } from '../../services/orders.service';
import { OrderDetail } from '../../models/orderDetail.model';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { MessageService } from '../../services/message.service';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrl: './order-detail.component.css'
})
export class OrderDetailComponent implements OnInit {
  actualMessageId: string = "";
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

  ngOnInit(): void {

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

  deleteMessage() {
    this.messageService.DeleteMessage(this.actualMessageId).subscribe(p => {
      window.location.reload();
    })
    this.modalService.dismissAll();
  }
}
