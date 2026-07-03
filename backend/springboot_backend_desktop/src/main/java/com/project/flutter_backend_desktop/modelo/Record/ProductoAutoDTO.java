package com.project.flutter_backend_desktop.modelo.Record;

public record ProductoAutoDTO(
        Integer id,
        String nombre,
        String marca,
        String codigo,
        Double precio
) {
}