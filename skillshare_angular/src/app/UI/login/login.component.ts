import { Component } from '@angular/core';
import { EmailValidator, FormControl, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor (private loginService: LoginService) {};
  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('')
  });

  submit():void {
    if(this.loginForm.valid) {
      this.login(this.loginForm.value.email!, this.loginForm.value.password!);
    } else {
      console.log('este formulario no es válido')
    }
  }

  validate(): boolean {
    
  }

  login(email: string, password: string) {
    this.loginService.LoginResponseUser(email, password).subscribe(p => {
      if(p.admin) {
        console.log('te has loggeado!');
        localStorage.setItem('TOKEN', p.token);
      } else {
        console.log('no puedes acceder, no eres admin');
      }
      
    });
  }
}
