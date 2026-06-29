package com.tuproyecto.backend.services;

import com.tuproyecto.backend.enums.RolUsuario;
import com.tuproyecto.backend.models.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias Funcionales - AuthService")
class AuthServiceTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private AuthService authService;

    private Usuario usuarioCliente;
    private Usuario usuarioAdmin;

    @BeforeEach
    void setUp() {
        usuarioCliente = new Usuario();
        usuarioCliente.setId(1L);
        usuarioCliente.setUsuario("cliente");
        usuarioCliente.setContrasena("cliente123");
        usuarioCliente.setRol(RolUsuario.CLIENTE);

        usuarioAdmin = new Usuario();
        usuarioAdmin.setId(2L);
        usuarioAdmin.setUsuario("admin");
        usuarioAdmin.setContrasena("admin123");
        usuarioAdmin.setRol(RolUsuario.ADMIN);
    }

    @Test
    @DisplayName("Login exitoso con credenciales correctas")
    void login_credencialesCorrectas_debeSerExitoso() {
        when(usuarioService.findByUsername("cliente")).thenReturn(Optional.of(usuarioCliente));

        boolean resultado = authService.login("cliente", "cliente123");

        assertTrue(resultado, "El login debería ser exitoso.");
        assertEquals(usuarioCliente, authService.getUsuarioActual(), "El usuario actual debe ser el que inició sesión.");
    }

    @Test
    @DisplayName("Login fallido con contraseña incorrecta")
    void login_contrasenaIncorrecta_debeFallar() {
        when(usuarioService.findByUsername("cliente")).thenReturn(Optional.of(usuarioCliente));

        boolean resultado = authService.login("cliente", "incorrecta");

        assertFalse(resultado, "El login debería fallar.");
        assertNull(authService.getUsuarioActual(), "No debe haber usuario actual tras un login fallido.");
    }

    @Test
    @DisplayName("Login fallido con usuario inexistente")
    void login_usuarioInexistente_debeFallar() {
        when(usuarioService.findByUsername("inexistente")).thenReturn(Optional.empty());

        boolean resultado = authService.login("inexistente", "password");

        assertFalse(resultado, "El login debería fallar.");
        assertNull(authService.getUsuarioActual(), "No debe haber usuario actual.");
    }

    @Test
    @DisplayName("Logout debe limpiar el usuario actual")
    void logout_debeLimpiarUsuarioActual() {
        // Primero, simula un login exitoso
        when(usuarioService.findByUsername("cliente")).thenReturn(Optional.of(usuarioCliente));
        authService.login("cliente", "cliente123");

        // Ahora, prueba el logout
        authService.logout();

        assertNull(authService.getUsuarioActual(), "El usuario actual debe ser nulo después del logout.");
    }

    @Test
    @DisplayName("isAdmin debe retornar true para un usuario ADMIN")
    void isAdmin_conUsuarioAdmin_debeRetornarTrue() {
        when(usuarioService.findByUsername("admin")).thenReturn(Optional.of(usuarioAdmin));
        authService.login("admin", "admin123");

        assertTrue(authService.isAdmin(), "Debe retornar true para un administrador.");
    }

    @Test
    @DisplayName("isAdmin debe retornar false para un usuario CLIENTE")
    void isAdmin_conUsuarioCliente_debeRetornarFalse() {
        when(usuarioService.findByUsername("cliente")).thenReturn(Optional.of(usuarioCliente));
        authService.login("cliente", "cliente123");

        assertFalse(authService.isAdmin(), "Debe retornar false para un no-administrador.");
    }

    @Test
    @DisplayName("isAdmin debe retornar false si no hay nadie logueado")
    void isAdmin_sinUsuarioLogueado_debeRetornarFalse() {
        assertFalse(authService.isAdmin(), "Debe retornar false si no hay sesión activa.");
    }
}
