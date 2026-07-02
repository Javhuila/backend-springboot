package com.project.flutter_backend_desktop.modelo.Record;

public record ClienteAutoDTO(
        Integer id,
        String nombreCompleto,
        Integer cedula,
        String telefono
) {
}