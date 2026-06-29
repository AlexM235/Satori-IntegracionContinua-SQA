package com.tuproyecto.backend.repositories;

import com.tuproyecto.backend.models.Producto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductoJsonRepository extends JsonRepository<Producto> {
    public ProductoJsonRepository() {
        super("productos.json", Producto.class);
    }

    public List<Producto> findAll() {
        return loadData();
    }

    public void saveAll(List<Producto> productos) {
        saveData(productos);
    }
}