import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { UsuarioService } from '../../../services/usuario.service';
import { Usuario } from '../../../models/usuario.model';
import { ModalPerfilClienteComponent } from '../../../view-perfiles/modal-perfil-cliente/modal-perfil-cliente.component';

@Component({
  selector: 'app-encabezado-cliente',
  standalone: true,
  imports: [CommonModule, RouterModule, ModalPerfilClienteComponent],
  templateUrl: './encabezado-cliente.component.html',
  styleUrls: ['./encabezado-cliente.component.css']
})
export class EncabezadoClienteComponent implements OnInit {
  mostrarModalPerfil = false;
  usuario!: Usuario;

  constructor(
    private authService: AuthService,
    private usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    const currentUser = this.authService.getCurrentUser();
    if (currentUser) {
      this.usuarioService.getByUsername(currentUser.usuario).subscribe({
        next: (usuario) => this.usuario = usuario,
        error: () => console.error('Error cargando perfil del usuario')
      });
    }
  }

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