import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {UsageTreeComponent} from './usage-tree.component';

describe('UsageTreeComponent', () => {
  let component: UsageTreeComponent;
  let fixture: ComponentFixture<UsageTreeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [UsageTreeComponent]
    })
        .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsageTreeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
