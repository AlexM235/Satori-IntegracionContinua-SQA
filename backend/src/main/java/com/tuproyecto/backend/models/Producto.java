package com.tuproyecto.backend.models;

import com.tuproyecto.backend.enums.TipoProducto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Producto {
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private String imagen;
    private TipoProducto tipo;
    private Integer cantidad;
    /*Esto de acá es para validar que ningún Precio debe ser negativo y que ningún nombre debe estar vacío, de momento
    Si buscan donde se usa, es en ProductoService*/
    public Producto(String nombre, double precio) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre no puede estar vacío");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("Precio debe ser positivo");
        }
        this.nombre = nombre;
        this.precio = precio;
    }
}