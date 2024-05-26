import { Component, TemplateRef, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../services/user.service';
import { UserDetail } from '../../models/userDetail.model';
import { Order } from '../../models/orders.model';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';

interface Alert {
	type: string;
	message: string;
}

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrl: './user-detail.component.css'
})
export class UserDetailComponent {

  alerts: Alert[] = [];
  user: UserDetail | undefined;
  private modalService = inject(NgbModal);
  closeResult = '';

  constructor(private route: ActivatedRoute, private userService: UserService) {
    this.reset();
    this.route.params.subscribe(p => {
      this.userService.GetUser(p['id']).subscribe(p => {
        this.user = p;
        this.isBanned(p.enabled);
      })
    })
  }

  deleteUserModal(content: TemplateRef<any>) {
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

  close(alert: Alert) {
		this.alerts.splice(this.alerts.indexOf(alert), 1);
	}

	reset() {
		this.alerts = [];
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

  deleteUser(id: string) {
    this.userService.DeleteUser(id).subscribe(() => {
      window.history.back();
    })
  }

  banUser(id: string) {
    this.userService.BanUser(id).subscribe({
      next: (p: UserDetail) => {
        this.user = p;
      this.isBanned(p.enabled);
      this.reset();
      this.alerts.push({type: 'success', message: `This user was successfully ${p.enabled? 'Unbanned' : 'Banned'}`});
      },
      error: (err) => {
        this.reset();
        this.alerts.push({type: 'danger', message: `${err.error.message}`});
      }
    });
  }

  givePrivileges(id: string) {
    this.userService.ChangePrivileges(id).subscribe({
      next: (p: UserDetail) => {
        this.user = p;
        this.reset();
        this.alerts.push({type: 'success', message: `Successfuly changed privileges, now using ${p.role} role`});
      },
      error: (err) => {
        this.reset();
        this.alerts.push({type: 'danger', message: `${err.error.message}`});
      }
    })
  }
}
