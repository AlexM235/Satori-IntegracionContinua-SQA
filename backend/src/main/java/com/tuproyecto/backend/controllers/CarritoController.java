package com.tuproyecto.backend.controllers;

import com.tuproyecto.backend.models.CarritoUpdateRequest;
import com.tuproyecto.backend.services.CarritoService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {
    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<?> getCarrito(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(carritoService.getProductosEnCarrito(usuarioId));
    }

    @GetMapping("/{usuarioId}/total")
    public ResponseEntity<Double> getTotal(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(carritoService.calcularTotal(usuarioId));
    }

    @PostMapping("/agregar")
    public ResponseEntity<Map<String, String>> agregarProductoAlCarrito(@RequestBody Map<String, Object> payload) {
        Map<String, String> response = new HashMap<>();
        try {
            Long usuarioId = Long.valueOf(payload.get("usuarioId").toString());
            Long productoId = Long.valueOf(payload.get("productoId").toString());

            boolean exito = carritoService.agregarProductoAlCarrito(usuarioId, productoId);

            if (exito) {
                response.put("mensaje", "Producto agregado al carrito");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "No se pudo agregar el producto al carrito");
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "Error del servidor: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // ✅ Nuevo endpoint para actualizar cantidades
    @PostMapping("/actualizar")
    public ResponseEntity<?> actualizarCarrito(@RequestBody CarritoUpdateRequest request) {
        try {
            boolean exito = carritoService.actualizarCarritoConCantidad(request.getUsuarioId(), request.getProductos());
            if (exito) {
                return ResponseEntity.ok("Carrito actualizado correctamente");
            } else {
                return ResponseEntity.badRequest().body("No se pudo actualizar el carrito");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{usuarioId}/eliminar/{productoId}")
public ResponseEntity<Map<String, String>> eliminarProductoDelCarrito(
        @PathVariable Long usuarioId,
        @PathVariable Long productoId) {

    Map<String, String> response = new HashMap<>();

    boolean exito = carritoService.eliminarProductoDelCarrito(usuarioId, productoId);

    if (exito) {
        response.put("mensaje", "Producto eliminado del carrito");
        return ResponseEntity.ok(response);
    } else {
        response.put("error", "No se pudo eliminar el producto del carrito");
        return ResponseEntity.badRequest().body(response);
    }
}

    @DeleteMapping("/vaciar/{usuarioId}")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Long usuarioId) {
    carritoService.vaciarCarrito(usuarioId);
    return ResponseEntity.ok().build();
}



    // DTO interno (lo puedes eliminar si ya no lo usas)
    public static class AgregarProductoRequest {
        private Long usuarioId;
        private Long productoId;

        public Long getUsuarioId() {
            return usuarioId;
        }

        public void setUsuarioId(Long usuarioId) {
            this.usuarioId = usuarioId;
        }

        public Long getProductoId() {
            return productoId;
        }

        public void setProductoId(Long productoId) {
            this.productoId = productoId;
        }
    }
}


