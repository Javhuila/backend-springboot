package com.project.flutter_backend_desktop.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class RecuperarContrasena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRecuperarContrasena;

    @Column(nullable = false)
    private Integer otp;

    @Column(nullable = false)
    private Date tiempoEspera;

    @OneToOne
    private Usuario usuario;
}
