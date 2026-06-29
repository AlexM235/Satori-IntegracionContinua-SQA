// app/models/producto.model.ts
export interface Producto {
  id: number; 
  nombre: string;
  descripcion: string;
  precio: number;
  imagen: string;
  tipo: 'RELOJ'; 
  cantidad: number;
}