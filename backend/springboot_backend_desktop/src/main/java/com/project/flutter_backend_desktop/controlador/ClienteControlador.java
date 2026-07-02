package com.project.flutter_backend_desktop.controlador;

import com.project.flutter_backend_desktop.modelo.Cliente;
import com.project.flutter_backend_desktop.modelo.Product;
import com.project.flutter_backend_desktop.modelo.Record.ClienteAutoDTO;
import com.project.flutter_backend_desktop.servicio.interfaz.IClienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/client")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:8080",
        "http://10.0.2.2:8080",
        "http://192.168.1.104:8080"
})
public class ClienteControlador {
    private static  final Logger logger = LoggerFactory.getLogger(ClienteControlador.class);

    @Autowired
    private IClienteServicio clienteServicio;

    @GetMapping("/clientes/list")
    public List<Cliente> listarClient() {
        var client = clienteServicio.listarCli();
        client.forEach(ap -> logger.info(ap.toString()));
        return client;
    }

    @GetMapping("/clientes/frecuentes")
    public List<Cliente> listarFrecuentes() {
        return clienteServicio.listarFrecuentes();
    }

    @PostMapping("/clientes")
    public ResponseEntity<Cliente> guardarClient(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteServicio.guardarCli(cliente);
        return ResponseEntity.ok(nuevoCliente);
    }

    @PutMapping("/editClientes/{id}")
    public Cliente actualizarClient(@PathVariable Integer id, @RequestBody Cliente cliente) {
        return clienteServicio.actualizarCli(cliente, id);
    }

    @GetMapping("/clientes/{id}/catch")
    public ResponseEntity<Cliente> obtenerClientPorId(@PathVariable Integer id) {
        Cliente cliente = clienteServicio.buscarClientePorId(id);
        if(cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/clientes/{id}/delete")
    public ResponseEntity<Map<String, Boolean>> eliminarClient(@PathVariable Integer id) {
        Cliente cliente = clienteServicio.buscarClientePorId(id);
        if(cliente == null) {
            return ResponseEntity.notFound().build();
        }
        clienteServicio.eliminarCli(cliente);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/clientes/filtro")
    public List<Cliente> filtrarClientes(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) Integer cedula
    ) {
        return clienteServicio.filtrarClientes(nombre, telefono, cedula);
    }

    @GetMapping("/clientes/autocomplete")
    public List<ClienteAutoDTO> autocompletarClientes(@RequestParam String nombreCompleto) {
        return clienteServicio.autocompleteClientes(nombreCompleto);
    }

    @GetMapping("/page-clientes")
    public Page<Cliente> listarPageClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
            ) {
        return clienteServicio.filtrarClientesPaginados(
                null,
                null,
                null,
                null,
                null,
                null,
                page,
                size,
                sortBy,
                direction
        );
    }
}