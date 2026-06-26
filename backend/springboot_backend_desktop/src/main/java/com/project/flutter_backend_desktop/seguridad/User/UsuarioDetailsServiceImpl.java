package com.project.flutter_backend_desktop.seguridad.User;

import com.project.flutter_backend_desktop.modelo.Usuario;
import com.project.flutter_backend_desktop.repositorio.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UsuarioRepositorio userRepositorio;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario user = userRepositorio.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el username:" + username));
        return UsuarioDetailsImpl.build(user);
    }
}
