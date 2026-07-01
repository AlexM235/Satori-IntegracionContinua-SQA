// producto.service.ts
import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Producto } from '../models/producto.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  private apiUrl = environment.apiUrl + '/api/productos';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.apiUrl);
  }

  create(producto: Omit<Producto, 'id'>): Observable<Producto> {
    return this.http.post<Producto>(this.apiUrl, producto);
  }

  createWithImage(formData: FormData): Observable<Producto> {
    return this.http.post<Producto>(this.apiUrl, formData);
  }

  update(id: number, producto: Producto): Observable<Producto> {
    return this.http.put<Producto>(`${this.apiUrl}/${id}`, producto);
  }

  updateWithImage(id: number, formData: FormData): Observable<Producto> {
    return this.http.put<Producto>(`${this.apiUrl}/${id}`, formData);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}