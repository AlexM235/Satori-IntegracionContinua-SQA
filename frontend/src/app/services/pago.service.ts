// app/services/pago.service.ts
import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Pago } from '../models/pago.model';

@Injectable({
  providedIn: 'root'
})
export class PagoService {
  private apiUrl = environment.apiUrl + '/api/pagos';

  constructor(private http: HttpClient) {}

  create(pago: Omit<Pago, 'id'>) {
    return this.http.post<Pago>(this.apiUrl, pago);
  }

  getByUsuario(usuario: string) {
    return this.http.get<Pago[]>(`${this.apiUrl}/usuario/${usuario}`);
  }

  updateEstado(pagoId: string, estado: string, razon?: string) {
    const params: any = { estado };
    if (razon) {
      params.razon = razon;
    }
    return this.http.patch(`${this.apiUrl}/${pagoId}/estado`, null, { params });
  }

  getEnums() {
    return this.http.get<{
      EstadoPago: string[],
      MetodoPago: string[]
    }>(`${this.apiUrl}/enums`);
  }

  getTodosLosPagos() {
  return this.http.get<Pago[]>(this.apiUrl);
  }
  eliminarPago(pagoId: string) {
  return this.http.delete(`${this.apiUrl}/${pagoId}`);
}



}
