package com.project.flutter_backend_desktop.servicio.interfaz;

import com.project.flutter_backend_desktop.modelo.Dto.ProductDTO;
import com.project.flutter_backend_desktop.modelo.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductServicio {

    List<Product> listarProducts();
    Product guardarProduct(ProductDTO productDTO);
    Product actualizarProduct(ProductDTO productDTO, Integer idProduct);
    void eliminarProduct(Product product);
    Product buscarProductPorId(Integer idProduct);

    String buscarCodigo();

    String uploadFoto(String idProduct, MultipartFile file);
}
