import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EncabezadoClienteComponent } from './encabezado-cliente.component';

describe('EncabezadoClienteComponent', () => {
  let component: EncabezadoClienteComponent;
  let fixture: ComponentFixture<EncabezadoClienteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EncabezadoClienteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EncabezadoClienteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
