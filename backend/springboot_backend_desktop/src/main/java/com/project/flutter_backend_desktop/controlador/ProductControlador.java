package com.project.flutter_backend_desktop.controlador;

import com.project.flutter_backend_desktop.modelo.Product;
import com.project.flutter_backend_desktop.servicio.IProductServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/product")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:8080",
        "http://10.0.2.2:8080",
        "http://192.168.1.104:8080"
})
public class ProductControlador {
    private static final Logger logger = LoggerFactory.getLogger(ProductControlador.class);

    @Autowired
    private IProductServicio productServicio;

    @GetMapping("/productos")
    public List<Product> listarProductos() {
        var product = productServicio.listarProducts();
        product.forEach(ap -> logger.info(ap.toString()));
        return product;
    }

    @PostMapping("/productos")
    public ResponseEntity<Product> guardarProducto(@RequestBody Product product) {
        Product nuevoProducto = productServicio.guardarProduct(product);
        return ResponseEntity.ok(nuevoProducto);
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Product> obtenerProductoPorId(@PathVariable int id) {
        Product product = productServicio.buscarProductPorId(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable int id) {
        Product product = productServicio.buscarProductPorId(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        productServicio.eliminarProduct(product);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
