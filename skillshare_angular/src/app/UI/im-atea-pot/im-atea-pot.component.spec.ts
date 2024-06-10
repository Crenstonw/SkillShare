import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImATeaPotComponent } from './im-atea-pot.component';

describe('ImATeaPotComponent', () => {
  let component: ImATeaPotComponent;
  let fixture: ComponentFixture<ImATeaPotComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ImATeaPotComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ImATeaPotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
