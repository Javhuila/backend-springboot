package com.project.flutter_backend_desktop.modelo;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "producto")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInventario;

    @OneToOne
    @JoinColumn(name = "producto_id", unique = true, nullable = false)
    private Product producto;

    @Column(nullable = false)
    private Integer stockActual;

    private LocalDateTime fechaActualizacion;

    @Version
    private Long version;
}
