package com.project.flutter_backend_desktop.repositorio;

import com.project.flutter_backend_desktop.modelo.Inventario;
import com.project.flutter_backend_desktop.modelo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface InventarioRepositorio extends JpaRepository<Inventario, Integer>, JpaSpecificationExecutor<Inventario> {

    Optional<Inventario> findByProducto(Product producto);

    List<Inventario> findByProductoIdProductIn(List<Integer> ids);
}
