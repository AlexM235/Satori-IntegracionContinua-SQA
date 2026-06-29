package com.tuproyecto.backend.controllers;

import com.tuproyecto.backend.models.Usuario;
import com.tuproyecto.backend.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")

public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Usuario> getUsuarioByUsername(@PathVariable String username) {
        return usuarioService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario actualizado) {
    Optional<Usuario> usuarioOpt = usuarioService.getById(id);
    if (usuarioOpt.isPresent()) {
        Usuario usuario = usuarioOpt.get();
        usuario.setUsuario(actualizado.getUsuario());
        usuario.setContrasena(actualizado.getContrasena());
        usuario.setCorreo(actualizado.getCorreo());
        usuario.setFoto(actualizado.getFoto()); // nueva propiedad

        usuarioService.guardarCambios(usuario);
        return ResponseEntity.ok(usuario);
    }
    return ResponseEntity.notFound().build();
}
@DeleteMapping("/{id}")
public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = usuarioService.eliminarPorId(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}