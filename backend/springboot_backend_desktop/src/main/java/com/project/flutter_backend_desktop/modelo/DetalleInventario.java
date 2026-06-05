package com.project.flutter_backend_desktop.modelo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.flutter_backend_desktop.modelo.Enum.TipoMovimiento;
import com.project.flutter_backend_desktop.modelo.Enum.TipoOperacion;
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
public class DetalleInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalleInventario;

    @ManyToOne
    @JoinColumn(name = "producto_id",
            nullable = false)
    private Product producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimiento tipo;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false, length = 100)
    private String referencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoOperacion tipoOperacion;

    @Column(length = 500)
    private String observacion;

    @Column(nullable = false)
    private Integer stockAnterior;

    @Column(nullable = false)
    private Integer stockNuevo;
}
