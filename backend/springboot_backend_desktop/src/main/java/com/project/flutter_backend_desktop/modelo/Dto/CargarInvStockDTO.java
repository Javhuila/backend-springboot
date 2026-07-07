package com.project.flutter_backend_desktop.modelo.Dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargarInvStockDTO {

    private Integer idProduct;

    private Integer cantidad;

    private String observacion;
}