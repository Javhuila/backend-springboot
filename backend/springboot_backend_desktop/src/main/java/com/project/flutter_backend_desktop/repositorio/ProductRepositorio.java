package com.project.flutter_backend_desktop.repositorio;

import com.project.flutter_backend_desktop.modelo.Enum.EstadoProducto;
import com.project.flutter_backend_desktop.modelo.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepositorio extends JpaRepository<Product, Integer>,
        JpaSpecificationExecutor<Product> {

    List<Product> findByNombre(String nombre);

    @Query(value = "select codigo from product where id_product=" +
            "(select max(id_product) from product);",
            nativeQuery = true)
    String buscarCodigo();

    @Query("SELECT MAX(p.idProduct) FROM Product p")
    Integer obtenerUltimoId();

    @Query("""
            SELECT p FROM Product p
            ORDER BY 
                CASE 
                    WHEN p.estado = 'ACTIVO' THEN 1
                    WHEN p.estado = 'INACTIVO' THEN 2
                    WHEN p.estado = 'DESCONTINUADO' THEN 3
                END
            """)
    List<Product> findAllOrderByEstadoPrioridad();

    List<Product> findByFrecuenteTrue();

    List<Product> findByFrecuenteTrueAndEstado(EstadoProducto estado);

    List<Product> findTop10ByNombreContainingIgnoreCase(String nombre);

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    @Query("""
            SELECT d.product
            FROM DetalleVenta d
            WHERE MONTH(d.venta.fechaVenta) = :mes
            AND YEAR(d.venta.fechaVenta) = :anio
            GROUP BY d.product
            ORDER BY SUM(d.cantidad) DESC
            """)
    List<Product> topProductosMasVendidos(@Param("mes") int mes,
                                          @Param("anio") int anio,
                                          Pageable pageable);

    @Query("""
            SELECT d.product
            FROM DetalleVenta d
            WHERE MONTH(d.venta.fechaVenta) = :mes
            AND YEAR(d.venta.fechaVenta) = :anio
            GROUP BY d.product
            ORDER BY SUM(d.cantidad) ASC
            """)
    List<Product> topProductosMenosVendidos(@Param("mes") int mes,
                                            @Param("anio") int anio,
                                            Pageable pageable);
}
