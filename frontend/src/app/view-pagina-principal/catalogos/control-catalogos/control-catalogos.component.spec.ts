import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlCatalogosComponent } from './control-catalogos.component';

describe('ControlCatalogosComponent', () => {
  let component: ControlCatalogosComponent;
  let fixture: ComponentFixture<ControlCatalogosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ControlCatalogosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ControlCatalogosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
