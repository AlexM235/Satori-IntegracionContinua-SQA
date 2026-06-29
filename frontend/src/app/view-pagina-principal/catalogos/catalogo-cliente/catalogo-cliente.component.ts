// catalogo-cliente.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ProductoService } from '../../../services/producto.service';
import { Producto } from '../../../models/producto.model';
import { CarritoService } from '../../../services/carrito.service';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-catalogo-cliente',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './catalogo-cliente.component.html',
  styleUrls: ['./catalogo-cliente.component.css']
})
export class CatalogoClienteComponent implements OnInit {
  productos: Producto[] = [];
  loading = true;
  error: string | null = null;

  constructor(
    private productoService: ProductoService,
    private carritoService: CarritoService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.loading = true;
    this.error = null;

    this.productoService.getAll().subscribe({
      next: (productos) => {
        this.productos = productos;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error al cargar los productos';
        this.loading = false;
        console.error('Error al cargar productos:', err);
      }
    });
  }

  formatearPrecio(precio: number): string {
    return '$' + precio.toFixed(2);
  }

  agregarAlCarrito(producto: Producto): void {
  if (!this.authService.isAuthenticated()) {
    alert('Debes iniciar sesión para añadir al carrito.');
    return;
  }

  const usuario = this.authService.getCurrentUser();
  if (usuario) {
    this.carritoService.agregarProducto(producto.id).subscribe({
      next: (res) => {
        console.log('Respuesta del backend:', res);
        alert('✅ Producto agregado al carrito exitosamente.');
      },
      error: (err) => {
        console.error('❌ Error al agregar al carrito:', err);
        alert('⚠️ Hubo un error al agregar el producto al carrito.');
      }
    });
  }
}
obtenerUrlImagen(nombreImagen: string | undefined): string {
  if (!nombreImagen) return 'assets/imagen-placeholder.jpg';
  if (nombreImagen.startsWith('http')) return nombreImagen;
  return nombreImagen;
}


}
