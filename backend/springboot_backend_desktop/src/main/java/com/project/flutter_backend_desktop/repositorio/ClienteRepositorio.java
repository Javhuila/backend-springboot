package com.project.flutter_backend_desktop.repositorio;

import com.project.flutter_backend_desktop.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNombre(String nombre);
}
