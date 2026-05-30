package com.project.flutter_backend_desktop.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.flutter_backend_desktop.modelo.Enum.TipoTarjeta;
import com.project.flutter_backend_desktop.modelo.Enum.TipoPago;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "venta")
@Data
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPago;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    @JsonIgnore
    @JsonBackReference
    private Venta venta;

    @Enumerated(EnumType.STRING)
    private TipoPago tipoPago;

    @Column(nullable = false)
    private Double monto;

    // Banco
    private String banco;

    private String numeroReferencia;

    @Enumerated(EnumType.STRING)
    private TipoTarjeta tipoTarjeta;

    private String ultimosDigitos;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaPago;

    private String comprobante;

    private String entidadFinanciera;

    @PrePersist
    public void prePersist() {
        fechaPago = LocalDateTime.now();
    }
}