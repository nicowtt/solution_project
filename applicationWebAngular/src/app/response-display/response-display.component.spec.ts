import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResponseDisplayComponent } from './response-display.component';

describe('ResponseDisplayComponent', () => {
  let component: ResponseDisplayComponent;
  let fixture: ComponentFixture<ResponseDisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResponseDisplayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResponseDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
