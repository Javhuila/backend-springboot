package com.project.flutter_backend_desktop.repositorio;

import com.project.flutter_backend_desktop.modelo.DetalleInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface DetalleInvRepositorio extends JpaRepository<DetalleInventario, Integer>,
        JpaSpecificationExecutor<DetalleInventario> {

    //Fecha entre inicio - final
    List<DetalleInventario> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}
