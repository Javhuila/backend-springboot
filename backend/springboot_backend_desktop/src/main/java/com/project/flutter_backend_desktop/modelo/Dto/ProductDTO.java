package com.project.flutter_backend_desktop.modelo.Dto;

import lombok.Data;

@Data
public class ProductDTO {

    private String nombre;
    private String contenido;
    private Double precio;
    private String marca;
    private String fotoUrl;

    private Integer categoriaId;
    private Integer clasificacionId;
}