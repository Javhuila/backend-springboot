package com.project.flutter_backend_desktop.modelo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.flutter_backend_desktop.modelo.Enum.EstadoVenta;
import com.project.flutter_backend_desktop.modelo.Enum.TipoPago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"detalles", "pagos",
        "cliente", "deuda"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idVenta;

    private String codigoVenta;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private LocalDate fechaVenta;

    private Double totalVenta;

    private Double cambio;

    private Integer totalItems;

    private String observacion;

    @Enumerated(EnumType.STRING)
    private EstadoVenta estado;

    private Boolean esDeuda = false;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Pago> pagos = new ArrayList<>();

    @OneToOne(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Deuda deuda;
}