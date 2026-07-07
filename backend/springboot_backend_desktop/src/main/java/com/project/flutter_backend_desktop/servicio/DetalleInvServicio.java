package com.project.flutter_backend_desktop.servicio;

import com.project.flutter_backend_desktop.Utils.ReferenciaInventarioHelper;
import com.project.flutter_backend_desktop.modelo.DetalleInventario;
import com.project.flutter_backend_desktop.modelo.Enum.TipoMovimiento;
import com.project.flutter_backend_desktop.modelo.Enum.TipoOperacion;
import com.project.flutter_backend_desktop.modelo.Inventario;
import com.project.flutter_backend_desktop.modelo.Product;
import com.project.flutter_backend_desktop.repositorio.DetalleInvRepositorio;
import com.project.flutter_backend_desktop.repositorio.InventarioRepositorio;
import com.project.flutter_backend_desktop.servicio.interfaz.IDetalleInventarioServicio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DetalleInvServicio implements IDetalleInventarioServicio {

    @Autowired
    private InventarioRepositorio inventarioRepositorio;

    @Autowired
    private DetalleInvRepositorio detalleRepositorio;

    @Transactional
    @Override
    public Inventario entradaProducto(
            Product product,
            Integer cantidad,
            TipoOperacion operacion,
            String referencia,
            String observacion) {
        validarCantidad(cantidad);

        return actualizarStock(
                product,
                cantidad,
                operacion,
                referencia,
                observacion,
                true
        );
    }

    @Transactional
    @Override
    public Inventario salidaProducto(Product product, Integer cantidad, TipoOperacion operacion, String referencia, String observacion) {
        validarCantidad(cantidad);

        return actualizarStock(
                product,
                cantidad,
                operacion,
                referencia,
                observacion,
                false
        );
    }

    private Inventario actualizarStock(
            Product product,
            Integer cantidad,
            TipoOperacion operacion,
            String referencia,
            String observacion,
            boolean entrada
    ) {

        try {

            Inventario inventario =
                    obtenerInventario(product);

            Integer stockAnterior =
                    inventario.getStockActual();

            Integer nuevoStock;

            if (entrada) {

                nuevoStock =
                        stockAnterior + cantidad;

            } else {

                if (stockAnterior < cantidad) {

                    throw new RuntimeException(
                            "Stock insuficiente para: "
                                    + product.getNombre()
                    );
                }

                nuevoStock =
                        stockAnterior - cantidad;
            }

            inventario.setStockActual(
                    nuevoStock
            );

            inventario.setFechaActualizacion(
                    LocalDateTime.now()
            );

            // FLUSH INMEDIATO
            Inventario guardado =
                    inventarioRepositorio
                            .saveAndFlush(inventario);

            if (referencia == null
                    || referencia.isBlank()) {

                referencia =
                        ReferenciaInventarioHelper
                                .generarReferencia(
                                        operacion,
                                        guardado.getIdInventario()
                                );
            }

            registrarMovimiento(
                    product,
                    cantidad,
                    operacion,
                    referencia,
                    observacion,
                    stockAnterior,
                    nuevoStock
            );

            return guardado;

        } catch (
                ObjectOptimisticLockingFailureException ex
        ) {

            throw new RuntimeException(
                    "El inventario del producto '"
                            + product.getNombre()
                            + "' fue modificado por otro usuario. "
                            + "Intente nuevamente."
            );
        }
    }

    @Transactional
    @Override
    public DetalleInventario registrarMovimiento(Product product, Integer cantidad, TipoOperacion operacion, String referencia, String observacion, Integer stockAnterior, Integer stockNuevo) {
        TipoMovimiento tipoMovimiento =
                operacion.getMovimientoDefecto();

        if (tipoMovimiento == null) {
            throw new RuntimeException(
                    "La operación requiere movimiento manual"
            );
        }

        DetalleInventario mov = new DetalleInventario();

        mov.setProducto(product);
        mov.setCantidad(cantidad);
        mov.setTipo(tipoMovimiento);
        mov.setFecha(LocalDateTime.now());
        mov.setReferencia(referencia);
        mov.setTipoOperacion(operacion);
        mov.setObservacion(observacion);
        mov.setStockAnterior(stockAnterior);
        mov.setStockNuevo(stockNuevo);

        return detalleRepositorio.save(mov);
    }

    private Inventario obtenerInventario(Product product) {

        return inventarioRepositorio.findByProducto(product)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Inventario no encontrado para: "
                                        + product.getNombre()
                        )
                );
    }

    private void validarCantidad(Integer cantidad) {

        if (cantidad == null || cantidad <= 0) {
            throw new RuntimeException(
                    "La cantidad debe ser mayor a cero"
            );
        }
    }
}