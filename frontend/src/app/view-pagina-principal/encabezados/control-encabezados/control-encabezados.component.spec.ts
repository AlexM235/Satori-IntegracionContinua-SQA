import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlEncabezadosComponent } from './control-encabezados.component';

describe('ControlEncabezadosComponent', () => {
  let component: ControlEncabezadosComponent;
  let fixture: ComponentFixture<ControlEncabezadosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ControlEncabezadosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ControlEncabezadosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
