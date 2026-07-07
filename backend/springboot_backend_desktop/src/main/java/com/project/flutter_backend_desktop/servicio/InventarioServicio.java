package com.project.flutter_backend_desktop.servicio;

import com.project.flutter_backend_desktop.modelo.DetalleInventario;
import com.project.flutter_backend_desktop.modelo.Dto.AjusteInventarioDTO;
import com.project.flutter_backend_desktop.modelo.Dto.CargarInvStockDTO;
import com.project.flutter_backend_desktop.modelo.Enum.TipoMovimiento;
import com.project.flutter_backend_desktop.modelo.Enum.TipoOperacion;
import com.project.flutter_backend_desktop.modelo.Inventario;
import com.project.flutter_backend_desktop.modelo.Product;
import com.project.flutter_backend_desktop.repositorio.DetalleInvRepositorio;
import com.project.flutter_backend_desktop.repositorio.InventarioRepositorio;
import com.project.flutter_backend_desktop.repositorio.ProductRepositorio;
import com.project.flutter_backend_desktop.servicio.interfaz.IDetalleInventarioServicio;
import com.project.flutter_backend_desktop.servicio.interfaz.IInventarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventarioServicio implements IInventarioServicio {

    @Autowired
    private InventarioRepositorio inventarioRepositorio;

    @Autowired
    private DetalleInvRepositorio detalleRepositorio;

    @Autowired
    private ProductRepositorio productRepositorio;

    @Autowired
    private IDetalleInventarioServicio detalleInventario;

    @Override
    public List<Inventario> listarInventario() {
        return inventarioRepositorio.findAll();
    }

    @Override
    public Inventario obtenerPorProducto(Integer idProducto) {
        Product producto = productRepositorio.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        return inventarioRepositorio.findByProducto(producto)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
    }

    @Transactional
    @Override
    public Inventario cargarStock(CargarInvStockDTO cargarInvDTO) {

        Product producto = productRepositorio.findById(cargarInvDTO.getIdProduct())
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado")
                );

        return detalleInventario.entradaProducto(
                producto,
                cargarInvDTO.getCantidad(),
                TipoOperacion.CARGA_STOCK,
                null,
                cargarInvDTO.getObservacion()
        );
    }

    @Transactional
    @Override
    public Inventario ajustarInventario(AjusteInventarioDTO dto) {

        Product producto = productRepositorio.findById(dto.getIdProduct())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (dto.getCantidad() == null || dto.getCantidad() <= 0) {
            throw new RuntimeException(
                    "La cantidad debe ser mayor a cero"
            );
        }
        TipoMovimiento movimiento =
                dto.getTipoOperacion()
                        .getMovimientoDefecto();
        if (movimiento == null) {
            throw new RuntimeException(
                    "La operación requiere definir movimiento manual"
            );
        }

        return movimiento == TipoMovimiento.ENTRADA
                ? detalleInventario.entradaProducto(
                producto,
                dto.getCantidad(),
                dto.getTipoOperacion(),
                dto.getReferencia(),
                dto.getObservacion()
        )
                : detalleInventario.salidaProducto(
                producto,
                dto.getCantidad(),
                dto.getTipoOperacion(),
                dto.getReferencia(),
                dto.getObservacion()
        );
    }

    @Transactional
    @Override
    public void eliminarInventario(Integer idInventario) {

        Inventario inventario = inventarioRepositorio.findById(idInventario)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        if (inventario.getStockActual() > 0) {
            throw new RuntimeException(
                    "No se puede eliminar inventario con stock"
            );
        }

        inventarioRepositorio.delete(inventario);
    }

    @Override
    public List<DetalleInventario> obtenerMovimientos() {
        return detalleRepositorio.findAll();
    }

    @Override
    public List<DetalleInventario> obtenerMovimientosPorFecha(LocalDate fecha) {

        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.atTime(23, 59, 59);

        return detalleRepositorio.findByFechaBetween(inicio, fin);
    }

    @Override
    public List<Inventario> filtrarInventario(String nombreProducto, String marca, LocalDateTime fecha) {

        Specification<Inventario> spec = (root, query, cb) -> cb.conjunction();

        if (nombreProducto != null  && !nombreProducto.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("producto").get("nombre"), "%" + nombreProducto + "%"));
        }

        if (marca != null && !marca.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("producto").get("marca"), "%" + marca + "%"));
        }

        if (fecha != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("fechaActualizacion"), fecha));
        }

        return inventarioRepositorio.findAll(spec);
    }

    @Override
    public List<DetalleInventario> filtrarDetalleInv(
            String producto,
            String marca,
            LocalDate fecha,
            TipoMovimiento tipo,
            String referencia,
            TipoOperacion tipoOperacion
    ) {

        Specification<DetalleInventario> spec = (root, query, cb) -> cb.conjunction();

        if (producto != null && !producto.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("producto").get("nombre"), "%" + producto + "%"));
        }

        if (marca != null && !marca.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("producto").get("marca"), "%" + marca + "%"));
        }

        if (fecha != null) {
            spec = spec.and((root, query, cb) -> {
                LocalDateTime inicio = fecha.atStartOfDay();
                LocalDateTime fin = fecha.atTime(23, 59, 59);
                return cb.between(root.get("fecha"), inicio, fin);
            });
        }

        if (tipo != null ) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("tipo"), tipo));
        }

        if (referencia != null && !referencia.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("referencia"), "%" + referencia + "%"));
        }

        if (tipoOperacion != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("tipoOperacion"), tipoOperacion));
        }

        return detalleRepositorio.findAll(spec);
    }
}