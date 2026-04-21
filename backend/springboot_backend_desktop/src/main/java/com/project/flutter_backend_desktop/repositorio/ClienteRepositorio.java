package com.project.flutter_backend_desktop.repositorio;

import com.project.flutter_backend_desktop.modelo.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer>,
        JpaSpecificationExecutor<Cliente> {

    @Query(value = "select cedula from cliente where id_cliente=" +
            "(select max(id_cliente) from cliente);",
            nativeQuery = true)
    Integer buscarCedula();

    List<Cliente> findTop10ByNombreCompletoContainingIgnoreCase(String nombreCompleto);

    Page<Cliente> findAll(Specification<Cliente> spec, Pageable pageable);
}
