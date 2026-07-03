package com.project.flutter_backend_desktop.controlador;

import com.project.flutter_backend_desktop.modelo.Dto.ProductDTO;
import com.project.flutter_backend_desktop.modelo.Enum.EstadoProducto;
import com.project.flutter_backend_desktop.modelo.Product;
import com.project.flutter_backend_desktop.modelo.Record.ProductoAutoDTO;
import com.project.flutter_backend_desktop.modelo.Request.PromoBonoRequestDTO;
import com.project.flutter_backend_desktop.modelo.Response.PromoBonoResponseDTO;
import com.project.flutter_backend_desktop.servicio.interfaz.IProductServicio;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.flutter_backend_desktop.Constant.Constant.PHOTO_DIRECTORY;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

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

    @GetMapping("/productos/list")
    @Operation(summary = "Listar todos los productos")
    public List<Product> listarProductos() {
        var product = productServicio.listarProducts();
        product.forEach(ap -> logger.info(ap.toString()));
        return product;
    }

    @GetMapping("/productos/frecuentes")
    public List<Product> listarFrecuentes() {
        return productServicio.listarFrecuentes();
    }

    @PostMapping("/productos")
    public ResponseEntity<Product> guardarProducto(
            @Valid @RequestBody ProductDTO productDTO) {
        Product nuevoProducto = productServicio.guardarProduct(productDTO);
        return ResponseEntity.ok(nuevoProducto);
    }

    @PutMapping("/editProductos/{id}")
    public Product actualizarProducto(@PathVariable Integer id,@RequestBody ProductDTO productDTO) {

        return productServicio.actualizarProduct(productDTO, id);
    }

    @GetMapping("/productos/{id}/catch")
    public ResponseEntity<Product> obtenerProductoPorId(@PathVariable int id) {
        Product product = productServicio.buscarProductPorId(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/productos/delete/{id}")
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

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> delProduct(@PathVariable int id) {
        productServicio.cambiarEstadoProducto(id, EstadoProducto.DESCONTINUADO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/productos/{id}/force")
    public ResponseEntity<?> eliminarProductoFisico(@PathVariable int id) {
        Product product = productServicio.buscarProductPorId(id);
        productServicio.deleteProduct(product);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/productos/{id}/estado")
    public ResponseEntity<Product> cambiarEstado(
            @PathVariable Integer id,
            @RequestParam EstadoProducto estado
    ) {
        Product actualizado = productServicio.cambiarEstadoProducto(id, estado);
        return ResponseEntity.ok(actualizado);
    }

    @PutMapping("/productos/photo")
    public ResponseEntity<String> uploadFoto(@RequestParam("id") String id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(productServicio.uploadFoto(id, file));
    }

    @GetMapping(path = "/productos/image/{filename}", produces = { IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE })
    public byte[] getFotografia(@PathVariable("filename") String filename) throws IOException {
        return Files.readAllBytes(Paths.get(PHOTO_DIRECTORY + filename));
    }

    @GetMapping("/productos/filtro")
    public List<Product> filtrarProductos(
            @RequestParam(required = false) String codigo,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Integer idCategoria,
            @RequestParam(required = false) Integer idClasificacion,
            @RequestParam(required = false) EstadoProducto estado,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return productServicio.filtrarProductos(
                codigo, nombre, marca,
                idCategoria, idClasificacion, estado,
                precioMin, precioMax,
                sortBy, direction
        );
    }

    @GetMapping("/productos/autocomplete")
    public List<ProductoAutoDTO> autocomplete(@RequestParam String nombre) {
        return productServicio.autocompleteProductos(nombre);
    }

    @GetMapping("/page-productos")
    public Page<Product> listarPageProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return productServicio.filtrarProductosPaginado(
                null, null, null, null, null,
                null, null, null,
                page, size, sortBy, direction
        );
    }

    @GetMapping("/productos/top-vendidos")
    public List<Product> topVendidos(
            @RequestParam int mes,
            @RequestParam int anio,
            @RequestParam(defaultValue = "5") int limit
    ) {
        return productServicio.topVendidosMes(mes, anio, limit);
    }

    @GetMapping("/productos/menos-vendidos")
    public List<Product> menosVendidos(
            @RequestParam int mes,
            @RequestParam int anio,
            @RequestParam(defaultValue = "5") int limit
    ) {
        return productServicio.menosVendidosMes(mes, anio, limit);
    }

    @GetMapping("/productos/top-vendidos/mes-actual")
    public List<Product> topVendidosMesActual(
            @RequestParam(defaultValue = "5") int limit
    ) {
        LocalDate hoy = LocalDate.now();
        return productServicio.topVendidosMes(hoy.getMonthValue(), hoy.getYear(), limit);
    }

    @GetMapping("promociones/{id}/bonificaciones")
    public List<PromoBonoResponseDTO> listarBonificaciones(@PathVariable Integer id) {
        return productServicio.listarBonificacionesProducto(id);
    }

    @PostMapping("promociones/{id}/bonificacions")
    public PromoBonoResponseDTO agregarBonificacion(@PathVariable Integer id,
                                                                @RequestBody PromoBonoRequestDTO promoDto) {
        return productServicio.agregarBonificacionProducto(id, promoDto);
    }

    @PutMapping("promociones/{id}/bonificacion/{idBonificacion}")
    public PromoBonoResponseDTO actualizarBonificacion(@PathVariable Integer id,
                                                       @PathVariable Integer idBonificacion,
                                                       @RequestBody PromoBonoRequestDTO promoDto) {
        return productServicio.actualizarBonificacionProducto(id, idBonificacion, promoDto);
    }

    @DeleteMapping("promociones/{id}/bonificaciones/{idBonificacion}")
    public ResponseEntity<?> eliminarBonificacion(@PathVariable Integer id,
                                                  @PathVariable Integer idBonificacion) {
        productServicio.eliminarBonificacionProducto(id, idBonificacion);
        return ResponseEntity.ok().build();
    }
}