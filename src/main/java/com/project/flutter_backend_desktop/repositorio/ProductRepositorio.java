package com.project.flutter_backend_desktop.repositorio;

import com.project.flutter_backend_desktop.modelo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepositorio extends JpaRepository<Product, Integer> {

    List<Product> findByNombre(String nombre);

    @Query(value = "select codigo from product where id_product=" +
            "(select max(id_product) from product);",
            nativeQuery = true)
    String buscarCodigo();
}
