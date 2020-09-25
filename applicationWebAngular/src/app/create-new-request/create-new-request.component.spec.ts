import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewRequestComponent } from './create-new-request.component';

describe('CreateNewRequestComponent', () => {
  let component: CreateNewRequestComponent;
  let fixture: ComponentFixture<CreateNewRequestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewRequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
