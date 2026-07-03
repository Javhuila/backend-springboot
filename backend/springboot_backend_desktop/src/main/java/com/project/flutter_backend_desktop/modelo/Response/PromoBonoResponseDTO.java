package com.project.flutter_backend_desktop.modelo.Response;
import com.project.flutter_backend_desktop.modelo.Enum.TipoPromocion;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoBonoResponseDTO {
    private Integer idBonificacion;
    private String nombre;
    private String nombreProductoOrigen;
    private String nombreProductoBono;

    private TipoPromocion tipoPromocion;
    private Double montoObjetivo;
    private Integer cantidadObjetivo;
    private Integer cantidadBonificacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean activo;
}