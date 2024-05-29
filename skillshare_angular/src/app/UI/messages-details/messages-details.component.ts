import { Component, OnInit, TemplateRef, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from '../../services/message.service';
import { UserService } from '../../services/user.service';
import { UserDetail } from '../../models/userDetail.model';
import { Message } from '../../models/orderMessages.model';
import { ModalDismissReasons, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { User } from '../../models/users.model';
import { DirectMessage } from '../../models/directChat.model';

@Component({
  selector: 'app-messages-details',
  templateUrl: './messages-details.component.html',
  styleUrl: './messages-details.component.css'
})
export class MessagesDetailsComponent implements OnInit{
  user: UserDetail | undefined;
  chat: DirectMessage[] | undefined;
  usersWhoTalkedWith: User[] | undefined;
  orderMessages: Message[] = [];
  private modalService = inject(NgbModal);
  closeResult = '';
  

  constructor(private route: ActivatedRoute, private userService: UserService, private messageService: MessageService) {
    this.route.params.subscribe(p => {
      this.userService.GetUser(p['id']).subscribe(p => {
        this.user = p;
      })
    })
  }

  deleteOrderMessageModal(content: TemplateRef<any>) {
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

  goBack() {
    window.history.back();
  }

  goToOrder(id: string) {
    window.location.pathname = `order/${id}`;
  }

  gotToUser(id: string) {
    window.location.pathname = `user/${id}`
  }

  dateScaler(dateTime: Date): string {
    const today = new Date();
    let date = dateTime.toString().split('T')[0].split('-');
    let time = dateTime.toString().split('T')[1].split(':');
    if(parseInt(date[0]) != today.getFullYear()) {
      return today.getFullYear() - parseInt(date[0]) + ` year${today.getFullYear() - parseInt(date[0]) != 1 ? 's' : ''} ago`;
    }else if(parseInt(date[1]) != (today.getMonth()+1)) {
      return ((today.getMonth()+1) - parseInt(date[1])) + ` month${((today.getMonth()+1) - parseInt(date[1])) != 1 ? 's' : ''} ago`;
    } else if(parseInt(date[2]) != today.getDate()) {
      return today.getDate() - parseInt(date[3]) + ` day${today.getDate() - parseInt(date[3]) != 1 ? 's' : ''} ago`;
    } else if(parseInt(time[0]) != today.getHours()) {
      return today.getHours() - parseInt(time[0]) + ` hour${(today.getHours() - parseInt(time[0])) != 1 ? 's' : ''} ago`;
    } else if(parseInt(time[1]) != today.getMinutes()) {
      return today.getMinutes() - parseInt(time[1]) + ` minute${today.getMinutes() - parseInt(time[1]) != 1 ? 's' : ''} ago`;
    } else {
      return 'just now';
    }
  }

  modalDeleteOrderMessage(id: string) {
    this.deleteOrderMessage(id);
    this.modalService.dismissAll('borrado');
  }

  getDirectMessageUserWhoTalkedWith(id: string) {
    this.messageService.GetDirectMessagesUserWhoTalkedWith(id).subscribe(p => {
      this.usersWhoTalkedWith = p;
    })
  }

  getChat(userTo: string) {
    this.messageService.GetChat(this.user!.id, userTo).subscribe(p => {
      this.chat = p;
    })
  }

  getUserOrderMessages() {
    this.messageService.GetOrderMessagesByUser(this.user!.id).subscribe(p => {
      this.orderMessages = p.content;
    })
  }

  deleteOrderMessage(id: string) {
    this.messageService.DeleteMessage(id).subscribe(() => {
      this.getUserOrderMessages();
    })
  }
}
