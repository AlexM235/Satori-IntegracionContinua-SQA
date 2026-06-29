import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
@Component({
  selector: 'app-encabezado-administrador',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './encabezado-administrador.component.html',
  styleUrls: ['./encabezado-administrador.component.css']
})
export class EncabezadoAdministradorComponent {
  mostrarModalPerfil = false;

  constructor(private authService: AuthService) {}

  cerrarSesion() {
    this.authService.logout().subscribe();
  }

  abrirModalPerfil() {
    this.mostrarModalPerfil = true;
  }

  cerrarModalPerfil() {
    this.mostrarModalPerfil = false;
  }
}
