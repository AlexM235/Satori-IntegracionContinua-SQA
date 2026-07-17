package com.tuproyecto.backend.services;

import com.tuproyecto.backend.enums.EstadoPago;
import com.tuproyecto.backend.enums.MetodoPago;
import com.tuproyecto.backend.models.Pago;
import com.tuproyecto.backend.repositories.PagosJsonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias Funcionales - PagoService")
class PagoServiceTest {

    @Mock
    private PagosJsonRepository pagosRepository;

    @InjectMocks
    private PagoService pagoService;

    private List<Pago> generarPagosMuestra() {
        List<Pago> lista = new ArrayList<>();
        Pago p1 = new Pago();
        p1.setId("uuid1");
        p1.setUsuario("usuario1");
        p1.setEstado(EstadoPago.PENDIENTE);
        lista.add(p1);

        Pago p2 = new Pago();
        p2.setId("uuid2");
        p2.setUsuario("usuario2");
        p2.setEstado(EstadoPago.CONFIRMADO);
        lista.add(p2);

        Pago p3 = new Pago();
        p3.setId("uuid3");
        p3.setUsuario("usuario1");
        p3.setEstado(EstadoPago.RECHAZADO);
        lista.add(p3);

        return lista;
    }

    @Test
    @DisplayName("crearPago debe asignar un ID y estado PENDIENTE, y guardarlo")
    void crearPago_debeAsignarIdYEstadoPendiente() {
        when(pagosRepository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(pagosRepository).saveAll(anyList());

        Pago nuevoPago = new Pago();
        nuevoPago.setUsuario("testUser");
        nuevoPago.setMonto(100.0);
        nuevoPago.setMetodo(MetodoPago.PAGO_MOVIL);

        Pago creado = pagoService.crearPago(nuevoPago);

        assertNotNull(creado.getId(), "El ID no debe ser nulo.");
        assertEquals(EstadoPago.PENDIENTE, creado.getEstado(), "El estado inicial debe ser PENDIENTE.");
        verify(pagosRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("getPagosByUsuario debe retornar solo los pagos del usuario especificado")
    void getPagosByUsuario_debeFiltrarCorrectamente() {
        when(pagosRepository.findAll()).thenReturn(generarPagosMuestra());

        List<Pago> resultado = pagoService.getPagosByUsuario("usuario1");

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(p -> p.getUsuario().equals("usuario1")));
    }

    @Test
    @DisplayName("getPagosByEstado debe retornar solo los pagos con el estado especificado")
    void getPagosByEstado_debeFiltrarCorrectamente() {
        when(pagosRepository.findAll()).thenReturn(generarPagosMuestra());

        List<Pago> resultado = pagoService.getPagosByEstado(EstadoPago.CONFIRMADO);

        assertEquals(1, resultado.size());
        assertEquals("uuid2", resultado.get(0).getId());
    }

    /*@Test
    @DisplayName("actualizarEstado debe cambiar el estado y la razón de un pago existente")
    void actualizarEstado_debeModificarPago() {
        List<Pago> pagos = generarPagosMuestra();
        when(pagosRepository.findAll()).thenReturn(pagos);
        doNothing().when(pagosRepository).saveAll(anyList());

        pagoService.actualizarEstado("uuid1", EstadoPago.CONFIRMADO, null);

        Pago pagoModificado = pagos.stream().filter(p -> p.getId().equals("uuid1")).findFirst().orElseThrow();
        assertEquals(EstadoPago.CONFIRMADO, pagoModificado.getEstado());
        assertNull(pagoModificado.getRazonRechazo());
        verify(pagosRepository, times(1)).saveAll(pagos);
    }

    @Test
    @DisplayName("actualizarEstado a RECHAZADO debe incluir la razón")
    void actualizarEstado_aRechazado_debeIncluirRazon() {
        List<Pago> pagos = generarPagosMuestra();
        when(pagosRepository.findAll()).thenReturn(pagos);
        doNothing().when(pagosRepository).saveAll(anyList());
        String razon = "Fondos insuficientes";

        pagoService.actualizarEstado("uuid1", EstadoPago.RECHAZADO, razon);

        Pago pagoModificado = pagos.stream().filter(p -> p.getId().equals("uuid1")).findFirst().orElseThrow();
        assertEquals(EstadoPago.RECHAZADO, pagoModificado.getEstado());
        assertEquals(razon, pagoModificado.getRazonRechazo());
        verify(pagosRepository, times(1)).saveAll(pagos);
    }*/


    @Test
    @DisplayName("getAllPagos debe retornar la lista completa de pagos")
    void getAllPagos_debeRetornarListaCompleta() {
        when(pagosRepository.findAll()).thenReturn(generarPagosMuestra());

        List<Pago> resultado = pagoService.getAllPagos();

        assertEquals(3, resultado.size());
        verify(pagosRepository, times(1)).findAll();
    }

    /*@Test
    @DisplayName("eliminarPago debe quitar el pago de la lista")
    void eliminarPago_debeQuitarPago() {
        List<Pago> pagos = new ArrayList<>(generarPagosMuestra());
        when(pagosRepository.findAll()).thenReturn(pagos);
        doNothing().when(pagosRepository).saveAll(anyList());

        pagoService.eliminarPago("uuid1");

        assertEquals(2, pagos.size());
        assertFalse(pagos.stream().anyMatch(p -> p.getId().equals("uuid1")));
        verify(pagosRepository, times(1)).saveAll(pagos);
    }*/
}
