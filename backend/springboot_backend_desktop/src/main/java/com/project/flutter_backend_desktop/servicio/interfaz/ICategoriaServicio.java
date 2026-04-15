package com.project.flutter_backend_desktop.servicio.interfaz;

import com.project.flutter_backend_desktop.modelo.Categoria;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICategoriaServicio {

    List<Categoria> listarCat();
    Categoria guardarCat(Categoria categoria);
    Categoria actualizarCat(Categoria categoria, Integer idCategoria);
    void eliminarCat(Categoria categoria);
    Categoria buscarCategoriaPorId(Integer idCategoria);

//    String buscarCategoria();

    String uploadFoto(String idCategoria, MultipartFile file);
}
