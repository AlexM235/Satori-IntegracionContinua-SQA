// app/services/carrito.service.ts
import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Producto } from '../models/producto.model';
import { Observable, throwError } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class CarritoService {
  private apiUrl = environment.apiUrl + '/api/carrito';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  agregarProducto(productoId: number): Observable<any> {
    const usuario = this.authService.getCurrentUser();

    if (!usuario || !usuario.id) {
      return throwError(() => new Error('Usuario no autenticado'));
    }

    const payload = {
      usuarioId: usuario.id,
      productoId: productoId
    };

    return this.http.post<void>(`${this.apiUrl}/agregar`, payload);
  }
  eliminarProducto(usuarioId: number, productoId: number) {
  return this.http.delete(`${this.apiUrl}/${usuarioId}/eliminar/${productoId}`);
}


  getProductos(usuarioId: number): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.apiUrl}/${usuarioId}`);
  }

  getTotal(usuarioId: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/${usuarioId}/total`);
  }

  vaciarCarrito(usuarioId: number) {
  return this.http.delete(`${this.apiUrl}/vaciar/${usuarioId}`);
}

}
