package com.project.flutter_backend_desktop.modelo.Request;

import com.project.flutter_backend_desktop.modelo.Enum.TipoPromocion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PromoBonoRequestDTO {

    @NotNull(message = "Debe seleccionar un producto bono")
    private Integer productoBonoId;

    @NotBlank(message = "El nombre de la promoción es obligatorio")
    private String nombre;

    @NotNull(message = "Debe seleccionar un tipo de promoción")
    private TipoPromocion tipoPromocion;

    @Positive(message = "El valor objetivo debe ser mayor a 0")
    private Double montoObjetivo;

    @Positive(message = "El valor objetivo debe ser mayor a 0")
    private Integer cantidadObjetivo;

    @Positive(message = "La cantidad de bonificación debe ser mayor a 0")
    private Integer cantidadBonificacion;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private Boolean activo = true;
}
