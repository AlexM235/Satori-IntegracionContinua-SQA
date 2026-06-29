import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalPerfilClienteComponent } from './modal-perfil-cliente.component';

describe('ModalPerfilClienteComponent', () => {
  let component: ModalPerfilClienteComponent;
  let fixture: ComponentFixture<ModalPerfilClienteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalPerfilClienteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalPerfilClienteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
