package com.project.flutter_backend_desktop.servicio.interfaz;

import com.project.flutter_backend_desktop.modelo.DetalleInventario;
import com.project.flutter_backend_desktop.modelo.Enum.TipoOperacion;
import com.project.flutter_backend_desktop.modelo.Inventario;
import com.project.flutter_backend_desktop.modelo.Product;

public interface IDetalleInventarioServicio {
    Inventario entradaProducto(
            Product product,
            Integer cantidad,
            TipoOperacion operacion,
            String referencia,
            String observacion
    );

    Inventario salidaProducto(
            Product product,
            Integer cantidad,
            TipoOperacion operacion,
            String referencia,
            String observacion
    );

    DetalleInventario registrarMovimiento(
            Product product,
            Integer cantidad,
            TipoOperacion operacion,
            String referencia,
            String observacion,
            Integer stockAnterior,
            Integer stockNuevo
    );
}