package com.project.flutter_backend_desktop.repositorio;

import com.project.flutter_backend_desktop.modelo.Product;
import com.project.flutter_backend_desktop.modelo.PromoBonoProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromoBonoProductRepositorio extends JpaRepository<PromoBonoProduct, Integer> {

    List<PromoBonoProduct> findByActivoTrue();

    List<PromoBonoProduct> findByProductoOrigenAndActivoTrue(Product product);

    List<PromoBonoProduct>findByProductoOrigenAndActivoTrueOrderByIdPromoBonoProductAsc(
            Product product
    );

    List<PromoBonoProduct>findByProductoOrigenAndActivoTrueOrderByFechaInicioDesc(Product product);
}
