import { Component, OnInit, TemplateRef, inject } from '@angular/core';
import { User } from '../../models/orders.model';
import { UserService } from '../../services/user.service';
import { FormControl, FormGroup } from '@angular/forms';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent implements OnInit{
  private modalService = inject(NgbModal);
  closeResult = '';
  items: User[] = [];
  newUserForm!: FormGroup;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.getUsers();
  }

  initForm() {
    this.newUserForm = new FormGroup({
      password: new FormControl(''),
      username: new FormControl(''),
      name: new FormControl(''),
      surname: new FormControl(''),
      email: new FormControl('')
    });
  }

  details(id: string) {
    window.location.pathname = `user/${id}`;
  }

  isAdmin(role: string) {
    if(role === '[ADMIN]')
      return true;
    else if(role === '[USER]')
      return false;
    else
      return false;
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

  newUserModal(content: TemplateRef<any>) {
    this.initForm();
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

  getUsers() {
    this.userService.GetUsers().subscribe(p => {
      this.items = p.content;
    })
  }

  newUser() {
    this.userService.NewUser(
      this.newUserForm.value.name, 
      this.newUserForm.value.surname,
      this.newUserForm.value.username,
      this.newUserForm.value.password,
      this.newUserForm.value.email,
    ).subscribe(() => {
      this.getUsers();
    })
  }

}
