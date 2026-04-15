package com.project.flutter_backend_desktop.servicio;

import Excepcion.RecursoNoEncontradoException;
import com.project.flutter_backend_desktop.modelo.Categoria;
import com.project.flutter_backend_desktop.modelo.Clasificacion;
import com.project.flutter_backend_desktop.modelo.Dto.ProductDTO;
import com.project.flutter_backend_desktop.modelo.Product;
import com.project.flutter_backend_desktop.repositorio.CategoriaRepositorio;
import com.project.flutter_backend_desktop.repositorio.ClasificacionRepositorio;
import com.project.flutter_backend_desktop.repositorio.ProductRepositorio;
import com.project.flutter_backend_desktop.servicio.interfaz.IProductServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.project.flutter_backend_desktop.Constant.Constant.PHOTO_DIRECTORY;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class ProductServicio implements IProductServicio {

    @Autowired
    private ProductRepositorio productRepositorio;

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Autowired
    private ClasificacionRepositorio clasificacionRepositorio;

    private static final Logger log = LoggerFactory.getLogger(ProductServicio.class);

    public Product getProduct(String id) {
        return productRepositorio.findById(Integer.valueOf(id)).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    public List<Product> listarProducts() {
        return productRepositorio.findAll();
    }

    @Override
    public Product buscarProductPorId(Integer idProduct) {
        Product product = productRepositorio.findById(idProduct).orElse(null);
        return product;
    }

    @Override
    public Product guardarProduct(ProductDTO dto) {
        Categoria categoria = categoriaRepositorio.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Clasificacion clasificacion = clasificacionRepositorio.findById(dto.getClasificacionId())
                .orElseThrow(() -> new RuntimeException("Clasificación no encontrada"));

        Product product = new Product();
        product.setCodigo(dto.getCodigo());
        product.setNombre(dto.getNombre());
        product.setContenido(dto.getContenido());
        product.setPrecio(dto.getPrecio());
        product.setMarca(dto.getMarca());
        product.setFotoUrl(dto.getFotoUrl());
        product.setCategoria(categoria);
        product.setClasificacion(clasificacion);

        return productRepositorio.save(product);
    }

    @Override
    public Product actualizarProduct(ProductDTO dto, Integer idProduct) {
        var productoActual = productRepositorio.findById(idProduct).orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));

        Categoria categoria = categoriaRepositorio.findById(dto.getCategoriaId())
                .orElseThrow();

        Clasificacion clasificacion = clasificacionRepositorio.findById(dto.getClasificacionId())
                .orElseThrow();

        productoActual.setCodigo(dto.getCodigo());
        productoActual.setNombre(dto.getNombre());
        productoActual.setMarca(dto.getMarca());
        productoActual.setContenido(dto.getContenido());
        productoActual.setPrecio(dto.getPrecio());
        productoActual.setFotoUrl(dto.getFotoUrl());
        productoActual.setCategoria(categoria);
        productoActual.setClasificacion(clasificacion);

        return productRepositorio.save(productoActual);
    }

    @Override
    public void eliminarProduct(Product product) {
        productRepositorio.delete(product);
    }

    @Override
    public String buscarCodigo() {
        String searchCodigo = productRepositorio.buscarCodigo();
        int nuevoCodigo = Integer.parseInt(searchCodigo);
        return String.valueOf(nuevoCodigo);
    }

    // Funcion de Servicio de foto I
    public String uploadFoto(String id, MultipartFile file) {
        log.info("Mensaje de foto para el usuario por ID: {}", id);
        Product product = getProduct(id);
        String fotoUrl = fotoFuncional.apply(id, file);
        product.setFotoUrl(fotoUrl);
        productRepositorio.save(product);
        return fotoUrl;
    }

    // Funcion de Servicio de foto III
    private final Function<String, String> fileExtension = filename -> Optional.of(filename).filter(name ->name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") +1)).orElse(".png");

    // Funcion de Servicio de foto II
    private final BiFunction<String, MultipartFile, String> fotoFuncional = (id, image) -> {
        String filename = id + fileExtension.apply(image.getOriginalFilename());

        try {
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();
            if(!Files.exists(fileStorageLocation)) { Files.createDirectories(fileStorageLocation); }
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(filename), REPLACE_EXISTING);
//            return ServletUriComponentsBuilder
//                    .fromCurrentContextPath()
//                    .path("/flutterDesktop/productos/image/" + filename)
//                    .toUriString();
            return filename;
        } catch (Exception exception) {
            log.error("Error al guardar la imagen: ", exception);
            throw new RuntimeException("No se puede guardar la imagen" + exception.getMessage(), exception);
        }
    };

}
