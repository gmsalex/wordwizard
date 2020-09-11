import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AddUsageComponent} from './add-usage.component';

describe('AddUsageComponent', () => {
  let component: AddUsageComponent;
  let fixture: ComponentFixture<AddUsageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AddUsageComponent]
    })
        .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUsageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
