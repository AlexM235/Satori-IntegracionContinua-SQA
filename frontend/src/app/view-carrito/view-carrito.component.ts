import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

import { CarritoService } from '../services/carrito.service';
import { AuthService } from '../services/auth.service';
import { Producto } from '../models/producto.model';

@Component({
  selector: 'app-view-carrito',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './view-carrito.component.html',
  styleUrl: './view-carrito.component.css'
})
export class ViewCarritoComponent implements OnInit {
  productos: Producto[] = [];

  constructor(
    private carritoService: CarritoService,
    private authService: AuthService,
    private http: HttpClient,
    private router: Router  // ✅ Inyectar Router aquí
  ) {}

  ngOnInit(): void {
    const usuario = this.authService.getCurrentUser();

    if (usuario) {
      this.carritoService.getProductos(usuario.id).subscribe({
        next: (productos: Producto[]) => {
          this.productos = productos;
        },
        error: (err: any) => {
          console.error('Error al obtener carrito:', err);
        }
      });
    }
  }

  formatearPrecio(precio: number): string {
    return precio.toFixed(2).replace('.', ',') + ' €';
  }

  incrementarCantidad(producto: Producto): void {
    producto.cantidad = (producto.cantidad || 1) + 1;
    this.actualizarCarrito();
  }

  disminuirCantidad(producto: Producto): void {
    if (producto.cantidad && producto.cantidad > 1) {
      producto.cantidad -= 1;
      this.actualizarCarrito();
    }
  }

  actualizarCarrito(): void {
    const usuario = this.authService.getCurrentUser();

    if (usuario) {
      this.http.post(`http://localhost:8080/api/carrito/actualizar`, {
        usuarioId: usuario.id,
        productos: this.productos
      }).subscribe({
        next: () => {
          // puedes mostrar mensaje o actualizar valores si deseas
        },
        error: err => {
          console.error('Error actualizando el carrito:', err);
        }
      });
    }
  }

  eliminarProducto(productoId: number): void {
    const usuario = this.authService.getCurrentUser();

    if (usuario) {
      this.carritoService.eliminarProducto(usuario.id, productoId).subscribe({
        next: () => {
          this.productos = this.productos.filter(p => p.id !== productoId);
        },
        error: err => {
          console.error('Error al eliminar el producto:', err);
        }
      });
    }
  }

  calcularTotal(): number {
    return this.productos.reduce((acc, producto) => {
      const cantidad = producto.cantidad || 1;
      return acc + (producto.precio * cantidad);
    }, 0);
  }

  procederAlPago(): void {
    const total = this.calcularTotal();
    console.log('Total calculado:', total); // ✅ Debug temporal
    localStorage.setItem('montoTotalCarrito', total.toString()); // ✅ Guardar en localStorage
    this.router.navigate(['/registrar-pago']); // ✅ Navegación correcta
  }
}



