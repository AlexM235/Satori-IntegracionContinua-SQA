// app/services/usuario.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Usuario } from '../models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = 'http://localhost:8080/api/usuarios';

  constructor(private http: HttpClient) {}

  // GET /api/usuarios -> Listar todos
  getAll() {
    return this.http.get<Usuario[]>(this.apiUrl);
  }

  // POST /api/usuarios -> Crear usuario
  create(usuario: { usuario: string, correo: string, contrasena: string }) {
    return this.http.post<Usuario>(this.apiUrl, {
      ...usuario,
      rol: 'CLIENTE', // Valor por defecto como en tu backend
      carrito: [] // Inicializado vacío como en tu lógica
    });
  }

  // GET /api/usuarios/{username} -> Buscar por username
  getByUsername(username: string) {
    return this.http.get<Usuario>(`${this.apiUrl}/${username}`);
  }
  // PUT /api/usuarios/{id}
actualizarUsuario(usuario: Usuario) {
  return this.http.put<Usuario>(`${this.apiUrl}/${usuario.id}`, usuario);
}
eliminarUsuario(id: number) {
  return this.http.delete(`${this.apiUrl}/${id}`);
}

}