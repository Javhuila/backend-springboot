package com.project.flutter_backend_desktop.controlador;

import com.project.flutter_backend_desktop.modelo.Categoria;
import com.project.flutter_backend_desktop.servicio.interfaz.ICategoriaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.flutter_backend_desktop.Constant.Constant.PHOTO_DIRECTORY;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("api/category")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:8080",
        "http://10.0.2.2:8080",
        "http://192.168.1.104:8080"
})
public class CategoriaControlador {
    private static final Logger logger = LoggerFactory.getLogger(ProductControlador.class);

    @Autowired
    private ICategoriaServicio categoriaServicio;

    @GetMapping("/categorias")
    public List<Categoria> listarCategory() {
        var category = categoriaServicio.listarCat();
        category.forEach(ap -> logger.info(ap.toString()));
        return category;
    }

    @PostMapping("/categorias")
    public ResponseEntity<Categoria> guardarCategory(@RequestBody Categoria categoria) {
        Categoria nuevoCategoria = categoriaServicio.guardarCat(categoria);
        return ResponseEntity.ok(nuevoCategoria);
    }

    @PutMapping("/editCategorias/{id}")
    public Categoria actualizarCategory(@PathVariable Integer id, @RequestBody Categoria categoria) {

        return categoriaServicio.actualizarCat(categoria, id);
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable Integer id) {
        Categoria categoria = categoriaServicio.buscarCategoriaPorId(id);
        if(categoria == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable int id) {
        Categoria categoria = categoriaServicio.buscarCategoriaPorId(id);
        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }
        categoriaServicio.eliminarCat(categoria);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/categorias/photo")
    public ResponseEntity<String> uploadFoto(@RequestParam("id") String id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(categoriaServicio.uploadFoto(id, file));
    }

    @GetMapping(value = "/categorias/image/{filename}", produces = { IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE })
    public byte[] getFotografia(@PathVariable String filename) throws IOException {
        return Files.readAllBytes(Paths.get(PHOTO_DIRECTORY + filename));
    }
}
