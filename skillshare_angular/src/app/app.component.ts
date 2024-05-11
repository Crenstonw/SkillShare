import { Component } from '@angular/core';
import { LoginComponent } from './UI/login/login.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'skillshare_angular';

  public show = true;

  toggleNavBar(component: any) {
    if (component instanceof LoginComponent) {
      this.show = false;
    } else {
      this.show = true;
    }
  }
}
