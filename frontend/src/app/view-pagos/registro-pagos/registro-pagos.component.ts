import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PagoService } from '../../services/pago.service';
import { Pago } from '../../models/pago.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-registro-pagos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './registro-pagos.component.html',
  styleUrls: ['./registro-pagos.component.css']
})
export class RegistroPagosComponent implements OnInit {
  pagos: Pago[] = [];
  pagoSeleccionado: Pago | null = null;
  mostrarModal: boolean = false;

  constructor(private pagoService: PagoService) {}

  ngOnInit(): void {
    this.pagoService.getTodosLosPagos().subscribe({
      next: (data) => {
        this.pagos = data;
      },
      error: (err) => {
        console.error('Error al cargar pagos:', err);
      }
    });
  }

  abrirModal(pago: Pago) {
    this.pagoSeleccionado = pago;
    this.mostrarModal = true;
  }

  cerrarModal() {
    this.mostrarModal = false;
    this.pagoSeleccionado = null;
  }

  eliminarPago(pagoId: string) {
    const confirmacion = confirm('¿Estás seguro de eliminar este pago? Esta acción no se puede deshacer.');
    if (confirmacion) {
      this.pagos = this.pagos.filter(p => p.id !== pagoId);
      // Aquí llamarías al backend para eliminarlo realmente.
    }
  }

  estadoClase(estado: string | undefined): string {
    switch (estado?.toUpperCase()) {
      case 'CONFIRMADO': return 'estado-confirmado';
      case 'RECHAZADO': return 'estado-rechazado';
      case 'PENDIENTE':
      default: return 'estado-pendiente';
    }
  }
}
