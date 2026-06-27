package com.project.flutter_backend_desktop.repositorio;

import com.project.flutter_backend_desktop.modelo.RecuperarContrasena;
import com.project.flutter_backend_desktop.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecuperarContrasenaRepositorio extends JpaRepository<RecuperarContrasena, Integer> {

    @Query("select id from RecuperarContrasena  id where id.otp = ?1 and id.usuario = ?2")
    Optional<RecuperarContrasena> findByOtpAndUsername(Integer  otp, Usuario user);

    Optional<RecuperarContrasena> findByUsuario(Usuario user);
}