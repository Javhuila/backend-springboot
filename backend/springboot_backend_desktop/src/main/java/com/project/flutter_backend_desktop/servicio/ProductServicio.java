package com.project.flutter_backend_desktop.servicio;

import com.project.flutter_backend_desktop.modelo.Product;
import com.project.flutter_backend_desktop.repositorio.ProductRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServicio implements IProductServicio{

    @Autowired
    private ProductRepositorio productRepositorio;

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
    public Product guardarProduct(Product product) {
        return productRepositorio.save(product);
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

}
