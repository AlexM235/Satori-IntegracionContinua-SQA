package com.tuproyecto.backend.services;

import com.tuproyecto.backend.enums.TipoProducto;
import com.tuproyecto.backend.models.Producto;
import com.tuproyecto.backend.repositories.ProductoJsonRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas Unitarias Funcionales — ProductoService
 *
 * Objetivo: Validar la exactitud de la lógica de negocio, manejo de excepciones
 * e interacción correcta con la capa de datos (Repositorio).
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias Funcionales — ProductoService")
class ProductoServiceTest {

    @Mock
    private ProductoJsonRepository repository;

    @InjectMocks
    private ProductoService productoService;

    // Helper reducido: No necesitamos miles de registros, solo una muestra representativa.
    private List<Producto> generarProductosMuestra() {
        List<Producto> lista = new ArrayList<>();
        Producto p1 = new Producto("Reloj A", 50.0);
        p1.setId(1L);
        Producto p2 = new Producto("Reloj B", 75.0);
        p2.setId(2L);
        lista.add(p1);
        lista.add(p2);
        return lista;
    }

    // -------------------------------------------------------------------------
    // 1. Pruebas de Recuperación de Datos (Lectura)
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("getAllProductos debe retornar la lista completa de productos")
    void getAllProductos_debeRetornarListaCompleta() {
        // Preparar (Arrange)
        List<Producto> productosMock = generarProductosMuestra();
        when(repository.findAll()).thenReturn(productosMock);

        // Actuar (Act)
        List<Producto> resultado = productoService.getAllProductos();

        // Afirmar (Assert)
        assertEquals(2, resultado.size(), "Debe retornar exactamente 2 productos");
        verify(repository, times(1)).findAll(); // Verifica que el servicio sí llamó al repositorio
    }

    @Test
    @DisplayName("findById debe retornar el producto si el ID existe")
    void findById_idExistente_debeRetornarProducto() {
        List<Producto> productosMock = generarProductosMuestra();
        when(repository.findAll()).thenReturn(productosMock);

        Optional<Producto> resultado = productoService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Reloj A", resultado.get().getNombre());
    }

    // -------------------------------------------------------------------------
    // 2. Pruebas de Lógica y Validaciones (¡Esta prueba ya la tenías perfecta!)
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("El sistema debe rechazar la creación de un producto con precio negativo")
    void producto_precioInvalido_debeLanzarExcepcion() {
        // Validamos la regla de negocio directamente
        assertThrows(IllegalArgumentException.class, () -> {
            new Producto("Reloj Invalido", -15.0);
        }, "Se esperaba una excepción por precio negativo");
    }

    // -------------------------------------------------------------------------
    // 3. Pruebas de Escritura y Modificación
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("crearProducto debe guardar el nuevo producto en el repositorio")
    void crearProducto_datosValidos_debeGuardarCorrectamente() {
        when(repository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(repository).saveAll(anyList());

        Producto nuevoProducto = new Producto("Nuevo Reloj", 100.0);

        Producto creado = productoService.crearProducto(nuevoProducto);

        assertNotNull(creado.getId(), "El producto creado debe tener un ID asignado");
        verify(repository, times(1)).saveAll(anyList()); // Validamos que se intentó guardar en la base de datos
    }
}
