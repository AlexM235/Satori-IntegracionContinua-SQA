package com.tuproyecto.backend.services;

import com.tuproyecto.backend.models.Producto;
import com.tuproyecto.backend.models.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias Funcionales - CarritoService")
class CarritoServiceTest {

    @Mock
    private ProductoService productoService;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private CarritoService carritoService;

    private Usuario usuario;
    private Producto producto1;
    private Producto producto2;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsuario("testUser");
        usuario.setCarrito(new ArrayList<>(List.of(1L, 1L, 2L))); // 2 de producto1, 1 de producto2

        producto1 = new Producto("Reloj A", 50.0);
        producto1.setId(1L);

        producto2 = new Producto("Reloj B", 75.0);
        producto2.setId(2L);
    }

    @Test
    @DisplayName("getProductosEnCarrito debe retornar productos con cantidad correcta")
    void getProductosEnCarrito_debeRetornarProductosAgrupados() {
        when(usuarioService.getAllUsuarios()).thenReturn(List.of(usuario));
        when(productoService.findById(1L)).thenReturn(Optional.of(producto1));
        when(productoService.findById(2L)).thenReturn(Optional.of(producto2));

        List<Producto> resultado = carritoService.getProductosEnCarrito(1L);

        assertEquals(2, resultado.size());
        resultado.forEach(p -> {
            if (p.getId().equals(1L)) {
                assertEquals(2, p.getCantidad());
            } else if (p.getId().equals(2L)) {
                assertEquals(1, p.getCantidad());
            }
        });
    }

    @Test
    @DisplayName("calcularTotal debe sumar el precio de todos los productos en el carrito")
    void calcularTotal_debeRetornarSumaCorrecta() {
        // Mock a getProductosEnCarrito para simplificar la prueba de calcularTotal
        // Esto es mejor que repetir toda la logica de getProductosEnCarrito aqui
        CarritoService spyCarritoService = spy(carritoService);
        List<Producto> productosEnCarrito = new ArrayList<>();
        Producto p1 = new Producto("Reloj A", 50.0); p1.setCantidad(2);
        Producto p2 = new Producto("Reloj B", 75.0); p2.setCantidad(1);
        productosEnCarrito.add(p1);
        productosEnCarrito.add(p2);
        doReturn(productosEnCarrito).when(spyCarritoService).getProductosEnCarrito(1L);

        double total = spyCarritoService.calcularTotal(1L);

        assertEquals(175.0, total, "El total debe ser (50*2) + (75*1) = 175");
    }

    @Test
    @DisplayName("agregarProductoAlCarrito debe añadir el ID del producto al carrito del usuario")
    void agregarProductoAlCarrito_debeAnadirId() {
        when(usuarioService.getAllUsuarios()).thenReturn(List.of(usuario));
        doNothing().when(usuarioService).guardarUsuarios(anyList());

        boolean resultado = carritoService.agregarProductoAlCarrito(1L, 3L);

        assertTrue(resultado);
        assertTrue(usuario.getCarrito().contains(3L));
        verify(usuarioService, times(1)).guardarUsuarios(anyList());
    }

    @Test
    @DisplayName("eliminarProductoDelCarrito debe quitar una instancia del producto")
    void eliminarProductoDelCarrito_debeQuitarUnProducto() {
        when(usuarioService.getAllUsuarios()).thenReturn(List.of(usuario));
        doNothing().when(usuarioService).guardarUsuarios(anyList());

        boolean resultado = carritoService.eliminarProductoDelCarrito(1L, 1L);

        assertTrue(resultado);
        assertEquals(2, usuario.getCarrito().size()); // Se eliminó uno, quedan 2
        assertEquals(1, usuario.getCarrito().stream().filter(id -> id.equals(1L)).count()); // Solo queda una instancia de producto 1
        verify(usuarioService, times(1)).guardarUsuarios(anyList());
    }

    @Test
    @DisplayName("vaciarCarrito debe eliminar todos los productos")
    void vaciarCarrito_debeDejarCarritoVacio() {
        when(usuarioService.getAllUsuarios()).thenReturn(List.of(usuario));
        doNothing().when(usuarioService).guardarUsuarios(anyList());

        carritoService.vaciarCarrito(1L);

        assertTrue(usuario.getCarrito().isEmpty());
        verify(usuarioService, times(1)).guardarUsuarios(anyList());
    }
}
