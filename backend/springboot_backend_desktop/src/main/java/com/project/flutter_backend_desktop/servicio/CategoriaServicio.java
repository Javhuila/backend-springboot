package com.project.flutter_backend_desktop.servicio;
import com.project.flutter_backend_desktop.servicio.interfaz.ICategoriaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.project.flutter_backend_desktop.modelo.Categoria;
import com.project.flutter_backend_desktop.repositorio.CategoriaRepositorio;
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
public class CategoriaServicio implements ICategoriaServicio {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    private static final Logger log = LoggerFactory.getLogger(CategoriaServicio.class);

    public Categoria getCat(String id) {
        return categoriaRepositorio.findById(Integer.valueOf(id)).orElseThrow(() -> new RuntimeException("Categoria no encontrado"));
    }

    @Override
    public List<Categoria> listarCat() {
        return categoriaRepositorio.findAll();
    }

    @Override
    public Categoria buscarCategoriaPorId(Integer idCategoria) {
        Categoria categoria = categoriaRepositorio.findById(idCategoria).orElse(null);
        return categoria;
    }

    @Override
    public Categoria guardarCat(Categoria categoria) {
        return categoriaRepositorio.save(categoria);
    }

    @Override
    public Categoria actualizarCat(Categoria categoria, Integer idCategoria) {
        Categoria existente = categoriaRepositorio.findById(idCategoria).orElseThrow();
        existente.setNombre(categoria.getNombre());
        existente.setFotoUrl(categoria.getFotoUrl());
        return categoriaRepositorio.save(existente);
    }

    @Override
    public void eliminarCat(Categoria categoria) {
        categoriaRepositorio.delete(categoria);
    }

    // Funcion de Servicio de foto I
    public String uploadFoto(String id, MultipartFile file) {
        log.info("Mensaje de foto para el usuario por ID: {}", id);
        Categoria cate = getCat(id);
        String fotoUrl = fotoFuncional.apply(id, file);
        cate.setFotoUrl(fotoUrl);
        categoriaRepositorio.save(cate);
        return fotoUrl;
    }
    // Funcion de Servicio de foto III
    private final Function<String, String> fileExtension =
            filename -> Optional
                    .of(filename)
                    .filter(name ->name.contains("."))
                    .map(name -> "." + name.substring(filename.lastIndexOf(".") +1))
                    .orElse(".png");

    // Funcion de Servicio de foto II
    private final BiFunction<String, MultipartFile, String> fotoFuncional = (id, image) -> {
        String filename = id + fileExtension.apply(image.getOriginalFilename());

        try {
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();
            if(!Files.exists(fileStorageLocation)) {Files.createDirectories(fileStorageLocation); }
                Files.copy(image.getInputStream(), fileStorageLocation.resolve(filename), REPLACE_EXISTING);
            // return ServletUriComponentsBuilder
                // .fromCurrentContextPath()
                // .path("/flutterDesktop/categorias/image/" + filename)
                // .toUriString();
            return filename;
        } catch (Exception exception) {
                log.error("Error al guardar la imagen: ", exception);
                throw new RuntimeException("No se puede guardar la imagen" + exception.getMessage(), exception);
        }
    };
}
