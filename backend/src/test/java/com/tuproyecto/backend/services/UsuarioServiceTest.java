package com.tuproyecto.backend.services;

import com.tuproyecto.backend.models.Usuario;
import com.tuproyecto.backend.repositories.UsuarioJsonRepository;
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
@DisplayName("Pruebas Unitarias Funcionales - UsuarioService")
class UsuarioServiceTest {

    @Mock
    private UsuarioJsonRepository repository;

    @InjectMocks
    private UsuarioService usuarioService;

    private List<Usuario> generarUsuariosMuestra() {
        List<Usuario> lista = new ArrayList<>();
        Usuario u1 = new Usuario();
        u1.setId(1L);
        u1.setUsuario("usuario1");
        lista.add(u1);

        Usuario u2 = new Usuario();
        u2.setId(2L);
        u2.setUsuario("usuario2");
        lista.add(u2);
        return lista;
    }

    @Test
    @DisplayName("crearUsuario debe asignar un ID y guardar el usuario")
    void crearUsuario_debeAsignarIdYGuardar() {
        when(repository.findAll()).thenReturn(new ArrayList<>());
        doNothing().when(repository).saveAll(anyList());

        Usuario nuevo = new Usuario();
        nuevo.setUsuario("nuevoUser");
        nuevo.setCorreo("nuevo@test.com");

        Usuario creado = usuarioService.crearUsuario(nuevo);

        assertNotNull(creado.getId(), "El ID no debería ser nulo después de crear.");
        verify(repository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("findByUsername debe retornar el usuario si existe")
    void findByUsername_siExiste_debeRetornarUsuario() {
        when(repository.findAll()).thenReturn(generarUsuariosMuestra());

        Optional<Usuario> resultado = usuarioService.findByUsername("usuario1");

        assertTrue(resultado.isPresent());
        assertEquals("usuario1", resultado.get().getUsuario());
    }

    @Test
    @DisplayName("findByUsername debe retornar vacío si no existe")
    void findByUsername_siNoExiste_debeRetornarVacio() {
        when(repository.findAll()).thenReturn(generarUsuariosMuestra());

        Optional<Usuario> resultado = usuarioService.findByUsername("fantasma");

        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("getAllUsuarios debe retornar la lista completa")
    void getAllUsuarios_debeRetornarListaCompleta() {
        when(repository.findAll()).thenReturn(generarUsuariosMuestra());

        List<Usuario> resultado = usuarioService.getAllUsuarios();

        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("eliminarPorId debe quitar el usuario si existe")
    void eliminarPorId_siExiste_debeEliminarUsuario() {
        List<Usuario> usuarios = new ArrayList<>(generarUsuariosMuestra());
        when(repository.findAll()).thenReturn(usuarios);
        doNothing().when(repository).saveAll(anyList());

        boolean eliminado = usuarioService.eliminarPorId(1L);

        assertTrue(eliminado);
        assertEquals(1, usuarios.size());
        verify(repository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("eliminarPorId no debe hacer nada si el usuario no existe")
    void eliminarPorId_siNoExiste_noDebeHacerNada() {
        List<Usuario> usuarios = new ArrayList<>(generarUsuariosMuestra());
        when(repository.findAll()).thenReturn(usuarios);

        boolean eliminado = usuarioService.eliminarPorId(99L);

        assertFalse(eliminado);
        assertEquals(2, usuarios.size());
        verify(repository, never()).saveAll(anyList());
    }
}
