import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [NgIf, FormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  usuario: string = '';
  correo: string = '';
  contrasena: string = '';
  confirmarContrasena: string = '';
  mensajeError: string = '';
  mensajeExito: string = '';
  cargando: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  registrar() {
    if (this.contrasena !== this.confirmarContrasena) {
      this.mensajeError = 'Las contraseñas no coinciden';
      return;
    }

    if (!this.usuario || !this.correo || !this.contrasena) {
      this.mensajeError = 'Por favor complete todos los campos';
      return;
    }

    this.cargando = true;
    this.mensajeError = '';
    this.mensajeExito = '';

    this.authService.register(this.usuario, this.correo, this.contrasena).subscribe({
      next: () => {
        this.mensajeExito = '¡Registro exitoso! Redirigiendo...';
        setTimeout(() => this.router.navigate(['/']), 2000);
      },
      error: (error) => {
        this.cargando = false;
        this.mensajeError = error.error?.message || 'Error en el registro';
      },
      complete: () => {
        this.cargando = false;
      }
    });
  }
}