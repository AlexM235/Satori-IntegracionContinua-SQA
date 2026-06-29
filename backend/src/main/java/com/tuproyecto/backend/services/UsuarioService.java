package com.tuproyecto.backend.services;

import com.tuproyecto.backend.enums.RolUsuario;
import com.tuproyecto.backend.models.Usuario;
import com.tuproyecto.backend.repositories.UsuarioJsonRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioJsonRepository repository;
    private Long nextId = 1L;

    public UsuarioService(UsuarioJsonRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        List<Usuario> usuarios = repository.findAll();
        if (usuarios.isEmpty()) {
            Usuario admin = new Usuario();
            admin.setId(nextId++);
            admin.setUsuario("admin");
            admin.setCorreo("admin@tienda.com");
            admin.setContrasena("admin123");
            admin.setRol(RolUsuario.ADMIN);
            repository.saveAll(List.of(admin));
        } else {
            nextId = usuarios.stream()
                    .mapToLong(Usuario::getId)
                    .max()
                    .orElse(0L) + 1L;
        }
    }

    public Usuario crearUsuario(Usuario usuario) {
        usuario.setId(nextId++);
        List<Usuario> usuarios = repository.findAll();
        usuarios.add(usuario);
        repository.saveAll(usuarios);
        return usuario;
    }

    public Optional<Usuario> findByUsername(String username) {
        return repository.findAll().stream()
                .filter(u -> u.getUsuario().equals(username))
                .findFirst();
    }

    public List<Usuario> getAllUsuarios() {
        return repository.findAll();
    }

    // necesario para guardar cambios en el JSON
    public void guardarUsuarios(List<Usuario> usuarios) {
        repository.saveAll(usuarios);
    }

public Optional<Usuario> getById(Long id) {
    return repository.findAll().stream().filter(u -> u.getId().equals(id)).findFirst();
}

    public void guardarCambios(Usuario actualizado) {
        List<Usuario> usuarios = repository.findAll().stream()
                .map(u -> {
                    if (u.getId().equals(actualizado.getId())) {
                        // Solo actualiza los campos permitidos
                        u.setUsuario(actualizado.getUsuario());
                        u.setCorreo(actualizado.getCorreo());
                        // Puedes validar si quieres permitir también cambiar foto:
                        if (actualizado.getFoto() != null) {
                            u.setFoto(actualizado.getFoto());
                        }
                    }
                    return u;
                })
                .toList();
        repository.saveAll(usuarios);
    }


    public boolean eliminarPorId(Long id) {
        List<Usuario> usuarios = repository.findAll();
        boolean eliminado = usuarios.removeIf(u -> u.getId().equals(id));
        if (eliminado) {
            repository.saveAll(usuarios);
        }
        return eliminado;
    }



}

