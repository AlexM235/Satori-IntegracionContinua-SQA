import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Usuario } from '../../models/usuario.model';
import { UsuarioService } from '../../services/usuario.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-modal-perfil-cliente',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './modal-perfil-cliente.component.html',
  styleUrls: ['./modal-perfil-cliente.component.css']
})
export class ModalPerfilClienteComponent implements OnInit {
  @Input() usuario!: Usuario;
  @Input() cerrar!: () => void;

  mostrarConfirmacion = false;
  mostrarVerificacionContrasena = false;
  mostrarUltimaConfirmacion = false;

  contrasena: string = '';
  error: string = '';
  mensajeConfirmacion: string = '';
  modoEdicion: boolean = false;

  constructor(
    private usuarioService: UsuarioService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (!this.usuario) {
      console.warn('ModalPerfilClienteComponent: No se recibió el usuario');
    }
  }

  cerrarModal() {
    if (this.cerrar) {
      this.cerrar();
    }
  }

  guardarCambios() {
    if (!this.usuario || !this.usuario.id) return;

    const datosActualizados = {
      id: this.usuario.id,
      usuario: this.usuario.usuario,
      correo: this.usuario.correo
    };

    this.usuarioService.actualizarUsuario(datosActualizados as Usuario).subscribe({
      next: () => {
        this.mensajeConfirmacion = 'Cambios guardados exitosamente.';
        this.modoEdicion = false;
        setTimeout(() => this.mensajeConfirmacion = '', 3000);
      },
      error: () => {
        this.error = 'No se pudo guardar los cambios.';
      }
    });
  }

  iniciarEliminacion() {
    this.mostrarConfirmacion = true;
  }

  confirmarPrimeraEtapa() {
    this.mostrarConfirmacion = false;
    this.mostrarVerificacionContrasena = true;
  }

  verificarContrasena() {
    if (this.contrasena !== this.usuario.contrasena) {
      this.error = 'Contraseña incorrecta';
      return;
    }

    this.error = '';
    this.mostrarVerificacionContrasena = false;
    this.mostrarUltimaConfirmacion = true;
  }

  confirmarEliminacionFinal() {
    this.usuarioService.eliminarUsuario(this.usuario.id!).subscribe({
      next: () => {
        this.authService.logout().subscribe();
        this.router.navigate(['/']);
      },
      error: () => {
        this.error = 'Error al eliminar el perfil';
      }
    });
  }

  cancelar() {
    this.mostrarConfirmacion = false;
    this.mostrarVerificacionContrasena = false;
    this.mostrarUltimaConfirmacion = false;
    this.contrasena = '';
    this.error = '';
  }
}
