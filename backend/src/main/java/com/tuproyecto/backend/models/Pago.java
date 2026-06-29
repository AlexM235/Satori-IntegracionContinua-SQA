package com.tuproyecto.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tuproyecto.backend.enums.EstadoPago;
import com.tuproyecto.backend.enums.MetodoPago;
import lombok.Data;

@Data
public class Pago {
    private String id;
    private String usuario;
    private MetodoPago metodo; // pago movil o transferencia
    private double monto;
    private String referencia;
    private EstadoPago estado; // pendiente, confirmado, rechazado
    private String razonRechazo;
    private String nombreTitular;
    private String bancoOrigen;
    private String fechaTransferencia;  // agregar aquí para no perder ese dato
     @JsonIgnore  // ✅ Esto evita que se guarde en pagos.json
    private String imagenComprobante;   // también agrega para guardar imagen base64
}

