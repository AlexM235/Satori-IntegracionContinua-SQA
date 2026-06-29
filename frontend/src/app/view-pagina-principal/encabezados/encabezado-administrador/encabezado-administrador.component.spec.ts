import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EncabezadoAdministradorComponent } from './encabezado-administrador.component';

describe('EncabezadoAdministradorComponent', () => {
  let component: EncabezadoAdministradorComponent;
  let fixture: ComponentFixture<EncabezadoAdministradorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EncabezadoAdministradorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EncabezadoAdministradorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
