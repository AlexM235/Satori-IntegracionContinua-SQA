// control-catalogos.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CatalogoClienteComponent } from '../catalogo-cliente/catalogo-cliente.component';
import { CatalogoAdministradorComponent } from '../catalogo-administrador/catalogo-administrador.component';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-control-catalogo',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    CatalogoClienteComponent,
    CatalogoAdministradorComponent
  ],
  templateUrl: './control-catalogos.component.html',
  styleUrls: ['./control-catalogos.component.css']
})
export class ControlCatalogosComponent {
  constructor(public authService: AuthService) {}
}