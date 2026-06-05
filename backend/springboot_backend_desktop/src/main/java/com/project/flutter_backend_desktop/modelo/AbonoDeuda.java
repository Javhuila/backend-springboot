package com.project.flutter_backend_desktop.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.flutter_backend_desktop.modelo.Enum.TipoPago;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AbonoDeuda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAbono;

    @ManyToOne
    @JoinColumn(name = "deuda_id")
    @JsonBackReference
    @JsonIgnore
    private Deuda deuda;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private LocalDate fechaAbono;

    @Enumerated(EnumType.STRING)
    private TipoPago tipoPago;

    private String banco;

    private String numeroReferencia;

    private String observacion;
}