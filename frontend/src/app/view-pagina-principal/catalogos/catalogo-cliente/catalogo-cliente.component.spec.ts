import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CatalogoClienteComponent } from './catalogo-cliente.component';

describe('CatalogoClienteComponent', () => {
  let component: CatalogoClienteComponent;
  let fixture: ComponentFixture<CatalogoClienteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CatalogoClienteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CatalogoClienteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
