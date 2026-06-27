package com.project.flutter_backend_desktop.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    //    @Size(max=15)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    @NotBlank
    private String username;

    //    @Size(max=10)
    @NotBlank
    private String password;

    private String nombre;

    private Boolean activo;

    //    @Size(min=7, max=11)
//    @NotBlank
    private String cedula;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    @OneToOne(mappedBy = "usuario")
    private RecuperarContrasena recuperarContrasena;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }
}