import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [NgIf, FormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  usuario: string = '';
  contrasena: string = '';
  mensajeError: string = '';
  cargando: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  iniciarSesion() {
    if (!this.usuario || !this.contrasena) {
      this.mensajeError = 'Por favor complete todos los campos';
      return;
    }

    this.cargando = true;
    this.mensajeError = '';

    this.authService.login(this.usuario, this.contrasena).subscribe({
      next: () => {
        this.router.navigate(['/']);
      },
      error: (error) => {
        this.cargando = false;
        this.mensajeError = error.error?.message || 'Error al iniciar sesión';
      },
      complete: () => {
        this.cargando = false;
      }
    });
  }
}