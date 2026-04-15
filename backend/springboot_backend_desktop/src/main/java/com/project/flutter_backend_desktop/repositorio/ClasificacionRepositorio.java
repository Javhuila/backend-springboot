package com.project.flutter_backend_desktop.repositorio;

import com.project.flutter_backend_desktop.modelo.Clasificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClasificacionRepositorio extends JpaRepository<Clasificacion, Integer> {

    List<Clasificacion> findByNombre(String nombre);
}