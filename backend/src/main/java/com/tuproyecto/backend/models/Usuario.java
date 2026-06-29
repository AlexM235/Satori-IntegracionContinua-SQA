package com.tuproyecto.backend.models;

import com.tuproyecto.backend.enums.RolUsuario;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Usuario {
    private Long id;
    private String usuario;
    private String correo;
    private String contrasena;
    private RolUsuario rol;
    private List<Long> carrito = new ArrayList<>();
    private String foto;
}
