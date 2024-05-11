import { Component } from '@angular/core';
import { EmailValidator, FormControl, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../../services/login.service';

interface Alert {
	type: string;
	message: string;
}

const ALERTS: Alert[] = [
	{
		type: 'danger',
		message: 'Your account can not access this website',
	},
]

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  alerts: Alert[] = [];

  constructor (private loginService: LoginService) {
  };

  close(alert: Alert) {
		this.alerts.splice(this.alerts.indexOf(alert), 1);
	}

	reset() {
		this.alerts = Array.from(ALERTS);
	}

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required)
  });

  submit():void {
    //if(this.validate())
      this.login(this.loginForm.value.email!, this.loginForm.value.password!);
  }

  validate(): boolean {
    if(this.loginForm.valid)
      return true;
    else
      return false;
  }

  login(email: string, password: string) {
    this.loginService.LoginResponseUser(email, password).subscribe(p => {
      if(p.admin) {
        console.log('te has loggeado!');
        localStorage.setItem('TOKEN', p.token);
        localStorage.setItem('ADMIN', p.username);
        window.location.pathname = '/home';
      } else {
        console.log('no puedes acceder, no eres admin');
        this.reset();
      }
      
    });
  }
}
