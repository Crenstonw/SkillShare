import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './UI/login/login.component';
import { PageNotFoundComponent } from './UI/page-not-found/page-not-found.component';
import { HomeComponent } from './UI/home/home.component';
import { OrdersComponent } from './UI/orders/orders.component';
import { OrderDetailComponent } from './UI/order-detail/order-detail.component';
import { TagsComponent } from './UI/tags/tags.component';
import { UsersComponent } from './UI/users/users.component';
import { UserDetailComponent } from './UI/user-detail/user-detail.component';
import { MessagesComponent } from './UI/messages/messages.component';
import { MessagesDetailsComponent } from './UI/messages-details/messages-details.component';

const routes: Routes = [
  { path: 'auth', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'order', component: OrdersComponent },
  { path: 'order/:id', component: OrderDetailComponent },
  { path: 'tag', component: TagsComponent },
  { path: 'user', component: UsersComponent },
  { path: 'user/:id', component: UserDetailComponent },
  { path: 'message', component: MessagesComponent },
  { path: 'message/:id', component: MessagesDetailsComponent },
  { path: '', pathMatch: 'full', redirectTo: '/home' },
  { path: '**', component: PageNotFoundComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
