package com.tuproyecto.backend.controllers;

import com.tuproyecto.backend.enums.TipoProducto;
import com.tuproyecto.backend.models.Producto;
import com.tuproyecto.backend.services.ProductoService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    private final ProductoService productoService;
    private final Path directorioImagenes = Paths.get("uploads");

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.getAllProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        return productoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Producto> crearProductoConImagen(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("tipo") String tipo,
            @RequestParam("precio") double precio,
            @RequestParam("cantidad") int cantidad,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen
    ) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setTipo(TipoProducto.valueOf(tipo));
        producto.setPrecio(precio);
        producto.setCantidad(cantidad);

        if (imagen != null && !imagen.isEmpty()) {
            try {
                Files.createDirectories(directorioImagenes);
                String filename = System.currentTimeMillis() + "_" + StringUtils.cleanPath(imagen.getOriginalFilename());
                Path rutaImagen = directorioImagenes.resolve(filename);
                Files.copy(imagen.getInputStream(), rutaImagen, StandardCopyOption.REPLACE_EXISTING);
                producto.setImagen("/imagenes/" + filename);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        Producto creado = productoService.crearProducto(producto);
        return ResponseEntity.ok(creado);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Producto> actualizarProductoConImagen(
            @PathVariable Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("tipo") String tipo,
            @RequestParam("precio") double precio,
            @RequestParam("cantidad") int cantidad,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen
    ) {
        Optional<Producto> existente = productoService.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Producto producto = existente.get();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setTipo(TipoProducto.valueOf(tipo));
        producto.setPrecio(precio);
        producto.setCantidad(cantidad);

        if (imagen != null && !imagen.isEmpty()) {
            try {
                Files.createDirectories(directorioImagenes);
                String filename = System.currentTimeMillis() + "_" + StringUtils.cleanPath(imagen.getOriginalFilename());
                Path rutaImagen = directorioImagenes.resolve(filename);
                Files.copy(imagen.getInputStream(), rutaImagen, StandardCopyOption.REPLACE_EXISTING);
                producto.setImagen("/imagenes/" + filename);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/enums")
    public ResponseEntity<Map<String, String[]>> getProductEnums() {
        Map<String, String[]> enums = new HashMap<>();
        enums.put("TipoProducto", Arrays.stream(TipoProducto.values())
                .map(Enum::name)
                .toArray(String[]::new));
        return ResponseEntity.ok(enums);
    }

    @GetMapping("/imagenes/{filename:.+}")
    public ResponseEntity<Resource> servirImagen(@PathVariable String filename) {
        try {
            Path archivo = directorioImagenes.resolve(filename).normalize();
            Resource recurso = new UrlResource(archivo.toUri());

            if (recurso.exists() || recurso.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + recurso.getFilename() + "\"")
                        .body(recurso);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
