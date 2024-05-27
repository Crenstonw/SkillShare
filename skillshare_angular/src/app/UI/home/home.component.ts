import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  adminName: string = localStorage.getItem('ADMIN')!;

  ngOnInit(): void {
    if(localStorage.getItem('TOKEN') === null)
      window.location.href = 'auth';
  }
}
