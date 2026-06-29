export interface Usuario {
  id: number;
  usuario: string;
  correo: string;
  contrasena?: string;
  rol: 'ADMIN' | 'CLIENTE';
  carrito: number[];
  foto?: string;
}