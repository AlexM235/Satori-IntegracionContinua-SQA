import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductoService } from '../../../services/producto.service';
import { Producto } from '../../../models/producto.model';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-catalogo-administrador',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './catalogo-administrador.component.html',
  styleUrls: ['./catalogo-administrador.component.css'],
})
export class CatalogoAdministradorComponent implements OnInit {
  mostrarModal = false;
  productos: Producto[] = [];
  loading = true;
  error: string | null = null;

  productoActual: Partial<Producto> = {
    nombre: '',
    descripcion: '',
    tipo: 'RELOJ',
    precio: 0,
    imagen: '',
    cantidad: 0
  };
  modoEdicion = false;
  enviandoDatos = false;
  imagenSeleccionada: File | null = null;
  vistaPreviaImagen: string | ArrayBuffer | null = null;

  constructor(private productoService: ProductoService) {}

  ngOnInit() {
    this.cargarProductos();
  }

  cargarProductos() {
    this.loading = true;
    this.error = null;
    
    this.productoService.getAll().pipe(
      finalize(() => this.loading = false)
    ).subscribe({
      next: (productos) => this.productos = productos,
      error: (err) => {
        console.error('Error cargando productos:', err);
        this.error = 'Error al cargar los productos. Intente nuevamente.';
      }
    });
  }

  abrirModal(producto?: Producto): void {
    if (producto) {
      this.modoEdicion = true;
      this.productoActual = { ...producto };
      this.vistaPreviaImagen = producto.imagen || null;
    } else {
      this.modoEdicion = false;
      this.productoActual = {
        nombre: '',
        descripcion: '',
        tipo: 'RELOJ',
        precio: 0,
        imagen: '',
        cantidad: 0
      };
      this.vistaPreviaImagen = null;
    }
    this.imagenSeleccionada = null;
    this.mostrarModal = true;
  }

  cerrarModal(): void {
    this.mostrarModal = false;
    this.imagenSeleccionada = null;
    this.vistaPreviaImagen = null;
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      this.imagenSeleccionada = input.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        this.vistaPreviaImagen = reader.result;
      };
      reader.readAsDataURL(this.imagenSeleccionada);
    }
  }

  guardarProducto(): void {
    if (this.enviandoDatos) return;

    this.enviandoDatos = true;

    const formData = new FormData();
    formData.append('nombre', this.productoActual.nombre || '');
    formData.append('descripcion', this.productoActual.descripcion || '');
    formData.append('tipo', this.productoActual.tipo || 'RELOJ');
    formData.append('precio', (this.productoActual.precio || 0).toString());
    formData.append('cantidad', (this.productoActual.cantidad || 0).toString());

    if (this.imagenSeleccionada) {
      formData.append('imagen', this.imagenSeleccionada);
    }

    if (this.modoEdicion && this.productoActual.id) {
      this.productoService.updateWithImage(this.productoActual.id, formData)
        .subscribe({
          next: () => {
            this.cargarProductos();
            this.cerrarModal();
          },
          error: (err) => {
            console.error('Error actualizando producto:', err);
            this.error = 'Error al actualizar el producto';
            this.enviandoDatos = false;
          }
        });
    } else {
      this.productoService.createWithImage(formData)
        .subscribe({
          next: () => {
            this.cargarProductos();
            this.cerrarModal();
            this.enviandoDatos = false; //Resetear después de cerrar
          },
          error: (err) => {
            console.error('Error creando producto:', err);
            this.error = 'Error al crear el producto';
            this.enviandoDatos = false;
          }
        });
    }
  }

  eliminarProducto(id: number): void {
    if (confirm('¿Estás seguro de eliminar este producto?')) {
      this.productoService.delete(id).subscribe({
        next: () => this.cargarProductos(),
        error: (err) => {
          console.error('Error eliminando producto:', err);
          this.error = 'Error al eliminar el producto';
        }
      });
    }
  }

  manejarErrorImagen(event: Event): void {
    const img = event.target as HTMLImageElement;
    img.src = 'assets/imagenes/default-product.png';
  }

  eliminarImagen(): void {
    this.imagenSeleccionada = null;
    this.vistaPreviaImagen = null;
    this.productoActual.imagen = '';
    const fileInput = document.getElementById('file-upload') as HTMLInputElement;
    if (fileInput) {
      fileInput.value = '';
    }
  }
}
