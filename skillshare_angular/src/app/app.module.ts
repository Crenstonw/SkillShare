import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './UI/login/login.component';
import { NgbAlertModule, NgbDropdownModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { PageNotFoundComponent } from './UI/page-not-found/page-not-found.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { HomeComponent } from './UI/home/home.component';
import { OrdersComponent } from './UI/orders/orders.component';
import { OrderCardsComponent } from './components/order-cards/order-cards.component';
import { OrderDetailComponent } from './UI/order-detail/order-detail.component';
import { TagsComponent } from './UI/tags/tags.component';
import { UsersComponent } from './UI/users/users.component';
import { UserDetailComponent } from './UI/user-detail/user-detail.component';
import { MessagesComponent } from './UI/messages/messages.component';
import { MessagesDetailsComponent } from './UI/messages-details/messages-details.component';
import { ImATeaPotComponent } from './UI/im-atea-pot/im-atea-pot.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    PageNotFoundComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    OrdersComponent,
    OrderCardsComponent,
    OrderDetailComponent,
    TagsComponent,
    UsersComponent,
    UserDetailComponent,
    MessagesComponent,
    MessagesDetailsComponent,
    ImATeaPotComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbAlertModule,
    NgbDropdownModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
