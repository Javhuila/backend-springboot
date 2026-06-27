package com.project.flutter_backend_desktop.repositorio;

import com.project.flutter_backend_desktop.modelo.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Transactional
    @Modifying
    @Query("update Usuario u set u.password = ?2 where u.username = ?1")
    void updatePassword(String username, String password);
}
