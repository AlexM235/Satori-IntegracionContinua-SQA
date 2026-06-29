import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EncabezadoVisitaComponent } from './encabezado-visita.component';

describe('EncabezadoVisitaComponent', () => {
  let component: EncabezadoVisitaComponent;
  let fixture: ComponentFixture<EncabezadoVisitaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EncabezadoVisitaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EncabezadoVisitaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
