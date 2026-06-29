// control-encabezados.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EncabezadoVisitaComponent } from '../encabezado-visita/encabezado-visita.component';
import { EncabezadoClienteComponent } from '../encabezado-cliente/encabezado-cliente.component';
import { EncabezadoAdministradorComponent } from '../encabezado-administrador/encabezado-administrador.component';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-control-encabezados',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    EncabezadoVisitaComponent,
    EncabezadoClienteComponent,
    EncabezadoAdministradorComponent
  ],
  templateUrl: './control-encabezados.component.html',
  styleUrls: ['./control-encabezados.component.css']
})
export class ControlEncabezadosComponent {
  constructor(public authService: AuthService) {}
}