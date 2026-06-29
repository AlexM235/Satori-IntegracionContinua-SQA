package com.tuproyecto.backend.services;

import com.tuproyecto.backend.models.Producto;
import com.tuproyecto.backend.models.Usuario;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarritoService {
    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    public CarritoService(ProductoService productoService, UsuarioService usuarioService) {
        this.productoService = productoService;
        this.usuarioService = usuarioService;
    }

    public List<Producto> getProductosEnCarrito(Long usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioService.getAllUsuarios().stream()
                .filter(u -> u.getId().equals(usuarioId))
                .findFirst();

        if (usuarioOpt.isEmpty()) return List.of();

        Usuario usuario = usuarioOpt.get();
        List<Long> carritoIds = usuario.getCarrito();

        Map<Long, Integer> cantidades = contarRepeticiones(carritoIds);

        return cantidades.entrySet().stream()
                .map(entry -> {
                    Optional<Producto> pOpt = productoService.findById(entry.getKey());
                    if (pOpt.isPresent()) {
                        Producto p = pOpt.get();
                        p.setCantidad(entry.getValue());
                        return p;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public double calcularTotal(Long usuarioId) {
        return getProductosEnCarrito(usuarioId).stream()
                .mapToDouble(p -> p.getPrecio() * p.getCantidad())
                .sum();
    }

    public boolean agregarProductoAlCarrito(Long usuarioId, Long productoId) {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();

        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(usuarioId)) {
                usuario.getCarrito().add(productoId);
                usuarioService.guardarUsuarios(usuarios);
                return true;
            }
        }

        return false;
    }

    //  actualizar cantidades directamente
    public boolean actualizarCarritoConCantidad(Long usuarioId, List<Producto> productosActualizados) {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();

        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(usuarioId)) {
                List<Long> nuevoCarrito = new ArrayList<>();
                for (Producto producto : productosActualizados) {
                    if (producto.getCantidad() != null && producto.getCantidad() > 0) {
                        for (int i = 0; i < producto.getCantidad(); i++) {
                            nuevoCarrito.add(producto.getId());
                        }
                    }
                }
                usuario.setCarrito(nuevoCarrito);
                usuarioService.guardarUsuarios(usuarios);
                return true;
            }
        }

        return false;
    }

    private Map<Long, Integer> contarRepeticiones(List<Long> ids) {
        Map<Long, Integer> conteo = new HashMap<>();
        for (Long id : ids) {
            conteo.put(id, conteo.getOrDefault(id, 0) + 1);
        }
        return conteo;
    }
    public boolean eliminarProductoDelCarrito(Long usuarioId, Long productoId) {
    List<Usuario> usuarios = usuarioService.getAllUsuarios();

    for (Usuario usuario : usuarios) {
        if (usuario.getId().equals(usuarioId)) {
            List<Long> carrito = usuario.getCarrito();

            if (carrito.contains(productoId)) {
                carrito.remove(productoId);  // ✅ Quita el producto
                usuarioService.guardarUsuarios(usuarios); // ✅ Guarda los cambios
                return true;
            }
        }
    }

    return false;
}
    public void vaciarCarrito(Long usuarioId) {
    List<Usuario> usuarios = usuarioService.getAllUsuarios();
    usuarios.stream()
        .filter(u -> u.getId().equals(usuarioId))
        .findFirst()
        .ifPresent(u -> {
            u.getCarrito().clear(); // Asume que el carrito es una lista de productos
            usuarioService.guardarUsuarios(usuarios);
        });
}


}

