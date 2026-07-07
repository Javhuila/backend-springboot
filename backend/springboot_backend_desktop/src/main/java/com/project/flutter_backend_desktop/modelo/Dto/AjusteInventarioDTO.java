package com.project.flutter_backend_desktop.modelo.Dto;

import com.project.flutter_backend_desktop.modelo.Enum.TipoMovimiento;
import com.project.flutter_backend_desktop.modelo.Enum.TipoOperacion;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AjusteInventarioDTO {
    private Integer idProduct;

    private Integer cantidad;

    private TipoOperacion tipoOperacion;

    private String referencia;

    private String observacion;
}