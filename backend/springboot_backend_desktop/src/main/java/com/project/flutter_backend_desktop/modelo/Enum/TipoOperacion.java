package com.project.flutter_backend_desktop.modelo.Enum;

public enum TipoOperacion {

    // =========================
    // OPERACIONES COMERCIALES
    // =========================

    BONIFICACION(
            TipoMovimiento.SALIDA,
            "BON"
    ),

    VENTA(
            TipoMovimiento.SALIDA,
            "VTA"
    ),

    DEVOLUCION(
            TipoMovimiento.ENTRADA,
            "DEV"
    ),

    ANULACION_VENTA(
            TipoMovimiento.ENTRADA,
            "ANV"
    ),

    // =========================
    // OPERACIONES INVENTARIO
    // =========================

    CARGA_STOCK(
            TipoMovimiento.ENTRADA,
            "CAR"
    ),

    REABASTECIMIENTO(
            TipoMovimiento.ENTRADA,
            "REB"
    ),

    AJUSTE_MANUAL(
            null,
            "AJT"
    ),

    // =========================
    // INCIDENTES
    // =========================

    ROBO(
            TipoMovimiento.SALIDA,
            "ROB"
    ),

    DANO(
            TipoMovimiento.SALIDA,
            "DAN"
    );

    private final TipoMovimiento movimientoDefecto;

    private final String prefijo;

    TipoOperacion(
            TipoMovimiento movimientoDefecto,
            String prefijo
    ) {
        this.movimientoDefecto = movimientoDefecto;
        this.prefijo = prefijo;
    }

    public TipoMovimiento getMovimientoDefecto() {
        return movimientoDefecto;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public boolean requiereMovimientoManual() {
        return movimientoDefecto == null;
    }
}