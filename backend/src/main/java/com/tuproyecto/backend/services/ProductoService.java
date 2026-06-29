package com.tuproyecto.backend.services;

import com.tuproyecto.backend.models.Producto;
import com.tuproyecto.backend.enums.TipoProducto;
import com.tuproyecto.backend.repositories.ProductoJsonRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    private final ProductoJsonRepository repository;
    private Long nextId = 1L;

    public ProductoService(ProductoJsonRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        if (repository.findAll().isEmpty()) {
            String baseUrl = "http://localhost:8080/imagenes/"; //Esto es para manejar la ruta de las imagenes
            // Producto 1
            Producto p1 = new Producto("Reloj POEDGAR Azul Plateado", 20.0);
            p1.setTipo(TipoProducto.RELOJ);
            p1.setImagen(baseUrl + "azul_plateado.jpg");
            p1.setCantidad(2000);
            crearProducto(p1);

            // Producto 2
            Producto p2 = new Producto("Reloj POEDGAR Blanco Plateado", 20.0);
            p2.setTipo(TipoProducto.RELOJ);
            p2.setImagen(baseUrl + "blanco_plateado.jpg");
            p2.setCantidad(2000);
            crearProducto(p2);

            // Producto 3
            Producto p3 = new Producto("Reloj POEDGAR Negro Plateado", 20.0);
            p3.setTipo(TipoProducto.RELOJ);
            p3.setImagen(baseUrl + "negro_plateado.jpg");
            p3.setCantidad(2000);
            crearProducto(p3);

            // Producto 4
            Producto p4 = new Producto("Reloj POEDGAR Verde Plateado", 20.0);
            p4.setTipo(TipoProducto.RELOJ);
            p4.setImagen(baseUrl + "verde_plateado.jpg");
            p4.setCantidad(2000);
            crearProducto(p4);
        } else {
            nextId = repository.findAll().stream()
                    .mapToLong(Producto::getId)
                    .max()
                    .orElse(0L) + 1L;
        }
    }

    public List<Producto> getAllProductos() {
        return repository.findAll();
    }

    public Producto crearProducto(Producto producto) {
        producto.setId(nextId++);
        List<Producto> productos = repository.findAll();
        productos.add(producto);
        repository.saveAll(productos);
        return producto;
    }

    public Optional<Producto> findById(Long id) {
        return repository.findAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public void actualizarProducto(Long id, Producto productoActualizado) {
        List<Producto> productos = repository.findAll();
        productos.replaceAll(p -> p.getId().equals(id) ? productoActualizado : p);
        repository.saveAll(productos);
    }

    public void eliminarProducto(Long id) {
        List<Producto> productos = repository.findAll();
        productos.removeIf(p -> p.getId().equals(id));
        repository.saveAll(productos);
    }
    public void guardarProducto(Producto producto) {
        producto.setId(nextId++);
        List<Producto> productos = repository.findAll();
        productos.add(producto);
        repository.saveAll(productos);
    }

}