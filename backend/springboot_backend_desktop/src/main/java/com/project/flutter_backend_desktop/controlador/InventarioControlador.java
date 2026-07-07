package com.project.flutter_backend_desktop.controlador;

import com.project.flutter_backend_desktop.modelo.DetalleInventario;
import com.project.flutter_backend_desktop.modelo.Dto.AjusteInventarioDTO;
import com.project.flutter_backend_desktop.modelo.Dto.CargarInvStockDTO;
import com.project.flutter_backend_desktop.modelo.Enum.TipoMovimiento;
import com.project.flutter_backend_desktop.modelo.Enum.TipoOperacion;
import com.project.flutter_backend_desktop.modelo.Inventario;
import com.project.flutter_backend_desktop.servicio.interfaz.IInventarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/invent")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:8080",
        "http://10.0.2.2:8080",
        "http://192.168.1.104:8080"
})
public class InventarioControlador {

    @Autowired
    private IInventarioServicio inventarioServicio;

    @GetMapping("/inventarios/list")
    public List<Inventario> listar() {
        return inventarioServicio.listarInventario();
    }

    @PostMapping("/inventarios/cargarInv")
    public Inventario cargarStock(@RequestBody CargarInvStockDTO dto) {
        return inventarioServicio.cargarStock(dto);
    }

    @PostMapping("/inventarios/ajuste")
    public Inventario ajustarInventario(
            @RequestBody AjusteInventarioDTO dto
    ) {
        return inventarioServicio.ajustarInventario(dto);
    }

    @DeleteMapping("/inventarios/delete/{id}")
    public ResponseEntity<?> eliminarInventario(
            @PathVariable Integer id
    ) {
        inventarioServicio.eliminarInventario(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/producto/{id}")
    public Inventario obtenerPorProducto(@PathVariable Integer id) {
        return inventarioServicio.obtenerPorProducto(id);
    }

    @GetMapping("/movInv")
    public List<DetalleInventario> movimientos() {
        return inventarioServicio.obtenerMovimientos();
    }

    @GetMapping("/movInv/{fecha}")
    public List<DetalleInventario> movimientosPorFecha(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha
    ) {
        return inventarioServicio.obtenerMovimientosPorFecha(fecha);
    }

    @GetMapping("/inventarios/filtro")
    public List<Inventario> filtrarInventario(
            @RequestParam(required = false) String product,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime fecha
    ) {
        return inventarioServicio.filtrarInventario(product, marca, fecha);
    }

    @GetMapping("/movInv/filtro")
    public List<DetalleInventario> filtrarMovimientos(
            @RequestParam(required = false) String product,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha,
            @RequestParam(required = false) TipoMovimiento tipo,
            @RequestParam(required = false) String referencia,
            @RequestParam(required = false) TipoOperacion tipoOperacion
    ) {
        return inventarioServicio.filtrarDetalleInv(
                product, marca, fecha, tipo, referencia, tipoOperacion
        );
    }
}