package com.project.flutter_backend_desktop.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.flutter_backend_desktop.modelo.Enum.EstadoDeuda;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"venta", "cliente"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Deuda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDeuda;

    @OneToOne
    @JoinColumn(name = "venta_id")
    @JsonIgnore
    private Venta venta;

    @ManyToOne
    private Cliente cliente;

    private Double totalDeuda;

    private Double saldoPendiente;

    private LocalDate fechaDeuda;

    private LocalDate fechaVencimiento;

    private String observacion;

    // Notificacion - No borrar
    private Boolean notificar;

    // Opcional
    private Integer diasGracia;

    private LocalDate fechaUltimoPago;

    @Enumerated(EnumType.STRING)
    private EstadoDeuda estado;

    @JsonManagedReference
    @OneToMany(mappedBy = "deuda", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<AbonoDeuda> abonos = new ArrayList<>();;
}