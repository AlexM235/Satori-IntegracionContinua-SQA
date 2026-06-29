import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { PagoService } from '../../services/pago.service';
import { CarritoService } from '../../services/carrito.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-registrar-pago',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './registrar-pago.component.html',
  styleUrls: ['./registrar-pago.component.css']
})
export class RegistrarPagoComponent implements OnInit {
  metodoSeleccionado: 'pago_movil' | 'transferencia' = 'pago_movil';
  montoSistema: number = 0;
  referencia: string = '';

  nombreTitular: string = '';
  cedulaTitular: string = '';
  bancoOrigen: string = '';
  fechaTransferencia: string = '';

  imagenBase64: string = '';

  constructor(
    private router: Router,
    private pagoService: PagoService,
    private carritoService: CarritoService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const usuario = this.authService.getCurrentUser();
    if (usuario) {
      this.carritoService.getTotal(usuario.id).subscribe(total => {
        this.montoSistema = total;
      });
    }
  }

  enviarPago() {
    const esValido = /^[0-9]{12,20}$/.test(this.referencia);
    if (!esValido) {
      alert('El número de referencia debe tener entre 12 y 20 dígitos numéricos.');
      return;
    }

    if (!this.imagenBase64) {
      alert('Por favor, sube una imagen del comprobante de pago.');
      return;
    }

    if (this.metodoSeleccionado === 'transferencia') {
      if (!this.nombreTitular || !this.cedulaTitular || !this.bancoOrigen || !this.fechaTransferencia) {
        alert('Por favor, completa todos los datos de la transferencia.');
        return;
      }
    }

    const usuarioActual = this.authService.getCurrentUser();
    if (!usuarioActual) {
      alert('No estás autenticado.');
      return;
    }

    this.pagoService.create({
      usuario: usuarioActual.usuario,
      metodo: this.metodoSeleccionado === 'pago_movil' ? 'PAGO_MOVIL' : 'TRANSFERENCIA',
      monto: this.montoSistema,
      referencia: this.referencia,
      nombreTitular: this.nombreTitular,
      bancoOrigen: this.bancoOrigen,
      fechaTransferencia: this.fechaTransferencia,
      imagenComprobante: this.imagenBase64
    }).subscribe({
      next: () => {
        // ✅ Vaciar carrito después de registrar pago
        this.carritoService.vaciarCarrito(usuarioActual.id).subscribe({
          next: () => {
            alert('¡Registro de pago completado!');
            this.router.navigate(['/catalogo']);
          },
          error: () => {
            alert('El pago se registró, pero hubo un error al vaciar el carrito.');
            this.router.navigate(['/catalogo']);
          }
        });
      },
      error: () => {
        alert('Error al registrar el pago');
      }
    });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      const file = input.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        this.imagenBase64 = reader.result as string;
      };
      reader.readAsDataURL(file);
    }
  }
  cancelarCompra(): void {
  const confirmacion = confirm('¿Estás seguro de que deseas cancelar la compra? Se vaciará tu carrito.');
  if (confirmacion) {
    const usuario = this.authService.getCurrentUser();
    if (usuario) {
      this.carritoService.vaciarCarrito(usuario.id).subscribe({
        next: () => {
          alert('Compra cancelada y carrito vaciado.');
          this.router.navigate(['/catalogo']);  // Redirigir al catálogo
        },
        error: err => {
          alert('Hubo un error al intentar cancelar la compra.');
          console.error(err);
        }
      });
    }
  }
}


}


