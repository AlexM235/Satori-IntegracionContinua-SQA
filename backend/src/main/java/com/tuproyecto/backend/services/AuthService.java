package com.tuproyecto.backend.services;

import com.tuproyecto.backend.enums.RolUsuario;
import com.tuproyecto.backend.models.Usuario;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UsuarioService usuarioService;
    private Usuario usuarioActual;

    public AuthService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public boolean login(String username, String password) {
        Optional<Usuario> usuario = usuarioService.findByUsername(username);
        if (usuario.isPresent() && usuario.get().getContrasena().equals(password)) {
            usuarioActual = usuario.get();
            return true;
        }
        return false;
    }

    public void logout() {
        usuarioActual = null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public boolean isAdmin() {
        return usuarioActual != null && usuarioActual.getRol() == RolUsuario.ADMIN;
    }
}