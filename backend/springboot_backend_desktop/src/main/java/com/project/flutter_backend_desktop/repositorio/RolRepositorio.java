package com.project.flutter_backend_desktop.repositorio;

import com.project.flutter_backend_desktop.modelo.Enum.ERol;
import com.project.flutter_backend_desktop.modelo.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, Integer> {

    Optional<Rol> findByName(ERol name);
}
