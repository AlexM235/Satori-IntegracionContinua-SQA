package com.tuproyecto.backend.controllers;

import com.tuproyecto.backend.enums.EstadoPago;
import com.tuproyecto.backend.enums.MetodoPago;
import com.tuproyecto.backend.models.Pago;
import com.tuproyecto.backend.services.PagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {
    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping
    public ResponseEntity<Pago> crearPago(@RequestBody Pago pago) {
        // Validar que metodo sea válido
        try {
            MetodoPago.valueOf(pago.getMetodo().name());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        Pago nuevoPago = pagoService.crearPago(pago);
        return ResponseEntity.ok(nuevoPago);
    }

    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<List<Pago>> getPagosByUsuario(@PathVariable String usuario) {
        List<Pago> pagos = pagoService.getPagosByUsuario(usuario);
        return ResponseEntity.ok(pagos);
    }

    @PatchMapping("/{pagoId}/estado")
    public ResponseEntity<Void> actualizarEstado(
            @PathVariable String pagoId,
            @RequestParam String estado,
            @RequestParam(required = false) String razon) {

        try {
            EstadoPago estadoPago = EstadoPago.valueOf(estado.toUpperCase());
            pagoService.actualizarEstado(pagoId, estadoPago, razon);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // Estado no válido
        }
    }

    @GetMapping("/enums")
    public ResponseEntity<Map<String, String[]>> getPaymentEnums() {
        Map<String, String[]> enums = new HashMap<>();

        enums.put("EstadoPago", Arrays.stream(EstadoPago.values())
                .map(Enum::name)
                .toArray(String[]::new));

        enums.put("MetodoPago", Arrays.stream(MetodoPago.values())
                .map(Enum::name)
                .toArray(String[]::new));

        return ResponseEntity.ok(enums);
    }

    @GetMapping
    public ResponseEntity<List<Pago>> getTodosLosPagos() {
    List<Pago> pagos = pagoService.getAllPagos();
    return ResponseEntity.ok(pagos);
}
@DeleteMapping("/{id}")
public ResponseEntity<Void> eliminarPago(@PathVariable String id) {
    pagoService.eliminarPago(id);
    return ResponseEntity.ok().build();
}


}
