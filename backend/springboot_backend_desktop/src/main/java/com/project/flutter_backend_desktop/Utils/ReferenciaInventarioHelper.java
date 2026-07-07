package com.project.flutter_backend_desktop.Utils;

import com.project.flutter_backend_desktop.modelo.Enum.TipoOperacion;

import java.time.LocalDate;

public class ReferenciaInventarioHelper {

    public static String generarReferencia(
            TipoOperacion operacion,
            Integer idReferencia
    ) {

        int year = LocalDate.now().getYear();

        return String.format(
                "%s-%d-%05d",
                operacion.getPrefijo(),
                year,
                idReferencia
        );
    }
}
