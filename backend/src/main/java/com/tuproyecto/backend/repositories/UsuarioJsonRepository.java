package com.tuproyecto.backend.repositories;

import com.tuproyecto.backend.models.Usuario;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioJsonRepository extends JsonRepository<Usuario> {
    public UsuarioJsonRepository() {
        super("usuarios.json", Usuario.class);
    }

    public List<Usuario> findAll() {
        return loadData();
    }

    public void saveAll(List<Usuario> usuarios) {
        saveData(usuarios);
    }
}