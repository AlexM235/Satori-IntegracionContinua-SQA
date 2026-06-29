package com.tuproyecto.backend.controllers;

import com.tuproyecto.backend.enums.RolUsuario;
import com.tuproyecto.backend.models.Usuario;
import com.tuproyecto.backend.services.AuthService;
import com.tuproyecto.backend.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UsuarioService usuarioService;

    public AuthController(AuthService authService, UsuarioService usuarioService) {
        this.authService = authService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        boolean success = authService.login(username, password);
        if (success) {
            return ResponseEntity.ok(authService.getUsuarioActual());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) {
        // Asignar rol CLIENTE por defecto
        usuario.setRol(RolUsuario.CLIENTE);
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/current")
public ResponseEntity<?> getCurrentUser(@RequestParam String username) {
    return usuarioService.findByUsername(username)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado"));
}



    @GetMapping("/enums")
    public Map<String, String[]> getAuthEnums() {
        Map<String, String[]> enums = new HashMap<>();
        enums.put("RolUsuario", Arrays.stream(RolUsuario.values())
                .map(Enum::name)
                .toArray(String[]::new));
        return enums;
    }
}