package com.tuproyecto.backend.models;

import lombok.Data;
import java.util.List;

@Data
public class CarritoUpdateRequest {
    private Long usuarioId;
    private List<Producto> productos;
}

