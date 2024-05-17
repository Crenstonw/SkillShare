import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './UI/login/login.component';
import { PageNotFoundComponent } from './UI/page-not-found/page-not-found.component';
import { HomeComponent } from './UI/home/home.component';
import { OrdersComponent } from './UI/orders/orders.component';
import { OrderDetailComponent } from './UI/order-detail/order-detail.component';
import { TagsComponent } from './UI/tags/tags.component';

const routes: Routes = [
  { path: 'auth', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'order', component: OrdersComponent },
  { path: 'order/:id', component: OrderDetailComponent },
  { path: 'tag', component: TagsComponent },
  { path: '', pathMatch: 'full', redirectTo: '/auth' },
  { path: '**', component: PageNotFoundComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
