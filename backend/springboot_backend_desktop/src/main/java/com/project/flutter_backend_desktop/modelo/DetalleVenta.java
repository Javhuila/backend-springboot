package com.project.flutter_backend_desktop.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "venta")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalleVenta;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    @JsonIgnore
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Product product;

    private Integer cantidad;

    private Double precioUnitario;

    private Double subtotal;

    private String observacion;

    @Column(nullable = false)
    private Boolean esBonificacion = false;
}