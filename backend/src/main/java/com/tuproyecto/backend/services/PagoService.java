package com.tuproyecto.backend.services;

import com.tuproyecto.backend.enums.EstadoPago;
import com.tuproyecto.backend.models.Pago;
import com.tuproyecto.backend.repositories.PagosJsonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PagoService {

    private final PagosJsonRepository pagosRepository;

    public PagoService(PagosJsonRepository pagosRepository) {
        this.pagosRepository = pagosRepository;
    }

    public Pago crearPago(Pago pago) {
        pago.setId(UUID.randomUUID().toString());
        pago.setEstado(EstadoPago.PENDIENTE);
        List<Pago> pagos = pagosRepository.findAll();
        pagos.add(pago);
        pagosRepository.saveAll(pagos);
        return pago;
    }

    public List<Pago> getPagosByUsuario(String usuario) {
        return pagosRepository.findAll().stream()
                .filter(p -> p.getUsuario().equals(usuario))
                .collect(Collectors.toList());
    }

    public List<Pago> getPagosByEstado(EstadoPago estado) {
        return pagosRepository.findAll().stream()
                .filter(p -> p.getEstado() == estado)
                .collect(Collectors.toList());
    }

    public void actualizarEstado(String pagoId, EstadoPago estado, String razon) {
        List<Pago> pagos = pagosRepository.findAll();
        pagos.stream()
                .filter(p -> p.getId().equals(pagoId))
                .findFirst()
                .ifPresent(p -> {
                    p.setEstado(estado);
                    p.setRazonRechazo(razon);
                });
        pagosRepository.saveAll(pagos);
    }

    public List<Pago> getAllPagos() {
        return pagosRepository.findAll();
    }

    public void eliminarPago(String id) {
        List<Pago> pagos = pagosRepository.findAll();
        pagos.removeIf(p -> p.getId().equals(id));
        pagosRepository.saveAll(pagos);
    }
}
