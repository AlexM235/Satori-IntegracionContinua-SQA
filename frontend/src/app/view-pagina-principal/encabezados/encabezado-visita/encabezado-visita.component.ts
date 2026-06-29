import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-encabezado-visita',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './encabezado-visita.component.html',
  styleUrls: ['./encabezado-visita.component.css']
})
export class EncabezadoVisitaComponent {
  // Eliminamos la inyección del servicio
  // Mantenemos solo la navegación
}