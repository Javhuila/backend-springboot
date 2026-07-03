package com.project.flutter_backend_desktop.modelo.Dto;

import com.project.flutter_backend_desktop.modelo.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResumenProductoVentaDTO {

    private Product producto;

    private Integer cantidad;

    private Double total;
}
