package com.project.flutter_backend_desktop.servicio.interfaz;

import com.project.flutter_backend_desktop.modelo.DetalleInventario;
import com.project.flutter_backend_desktop.modelo.Dto.AjusteInventarioDTO;
import com.project.flutter_backend_desktop.modelo.Dto.CargarInvStockDTO;
import com.project.flutter_backend_desktop.modelo.Enum.TipoMovimiento;
import com.project.flutter_backend_desktop.modelo.Enum.TipoOperacion;
import com.project.flutter_backend_desktop.modelo.Inventario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IInventarioServicio {
    List<Inventario> listarInventario();

    Inventario obtenerPorProducto(Integer idProduct);

    Inventario cargarStock(CargarInvStockDTO cargarInvDTO);

    Inventario ajustarInventario(AjusteInventarioDTO dto);

    void eliminarInventario(Integer idInventario);

    List<DetalleInventario> obtenerMovimientos();

    List<DetalleInventario> obtenerMovimientosPorFecha(LocalDate fecha);

    List<Inventario> filtrarInventario(
            String nombreProducto,
            String marca,
            LocalDateTime fecha
    );

    List<DetalleInventario> filtrarDetalleInv(
            String product,
            String marca,
            LocalDate fecha,
            TipoMovimiento tipo,
            String referencia,
            TipoOperacion tipoOperacion
    );
}
