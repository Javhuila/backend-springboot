package com.project.flutter_backend_desktop.modelo;

import com.project.flutter_backend_desktop.modelo.Enum.TipoPromocion;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromoBonoProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPromoBonoProduct;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private TipoPromocion tipoPromocion;

    private Double montoObjetivo;

    private Integer cantidadBonificacion;

    private Integer cantidadObjetivo;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @ManyToOne
    @JoinColumn(name = "producto_bonificacion_id")
    private Product productoBonificacion;

    @ManyToOne
    @JoinColumn(name = "producto_origen_id")
    private Product productoOrigen;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    private Boolean activo = true;
}
