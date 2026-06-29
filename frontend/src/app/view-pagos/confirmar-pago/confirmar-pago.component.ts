import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PagoService } from '../../services/pago.service';

@Component({
  selector: 'app-confirmar-pago',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './confirmar-pago.component.html',
  styleUrls: ['./confirmar-pago.component.css']
})
export class ConfirmarPagoComponent implements OnInit {
  pagosPendientes: any[] = [];
  selectedId: string | null = null;
  razonRechazo: string = '';
  imagenAmpliada: string | null = null; // para el modal

  constructor(private pagoService: PagoService) {}

  ngOnInit(): void {
    this.pagoService.getTodosLosPagos().subscribe(pagos => {
      this.pagosPendientes = pagos.filter(p => p.estado === 'PENDIENTE');
    });
  }

  confirmar(id: string) {
    this.pagoService.updateEstado(id, 'CONFIRMADO').subscribe(() => {
      this.pagosPendientes = this.pagosPendientes.filter(p => p.id !== id);
    });
  }

  toggleRechazo(id: string) {
    this.selectedId = this.selectedId === id ? null : id;
    this.razonRechazo = '';
  }

  rechazar(id: string) {
    if (this.razonRechazo.trim()) {
      this.pagoService.updateEstado(id, 'RECHAZADO', this.razonRechazo).subscribe(() => {
        this.pagosPendientes = this.pagosPendientes.filter(p => p.id !== id);
        this.selectedId = null;
      });
    } else {
      alert('Debes ingresar una razón del rechazo');
    }
  }

  abrirModal(imagen: string): void {
    this.imagenAmpliada = imagen;
  }

  cerrarModal(): void {
    this.imagenAmpliada = null;
  }
}


