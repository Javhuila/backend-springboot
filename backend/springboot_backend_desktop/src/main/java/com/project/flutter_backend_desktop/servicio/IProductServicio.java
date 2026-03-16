package com.project.flutter_backend_desktop.servicio;

import com.project.flutter_backend_desktop.modelo.Product;

import java.util.List;

public interface IProductServicio {

    List<Product> listarProducts();
    Product guardarProduct(Product product);
    void eliminarProduct(Product product);
    Product buscarProductPorId(Integer idProduct);

    String buscarCodigo();

}
