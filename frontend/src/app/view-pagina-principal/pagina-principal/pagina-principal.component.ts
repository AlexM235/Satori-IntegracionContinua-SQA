import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ControlEncabezadosComponent } from '../encabezados/control-encabezados/control-encabezados.component';
import { ControlCatalogosComponent } from '../catalogos/control-catalogos/control-catalogos.component';

@Component({
  selector: 'app-pagina-principal',
  standalone: true,
  imports: [
    CommonModule,
    ControlEncabezadosComponent,
    ControlCatalogosComponent
  ],
  templateUrl: './pagina-principal.component.html',
  styleUrls: ['./pagina-principal.component.css']
})
export class PaginaPrincipalComponent {
  // Componente contenedor principal
}