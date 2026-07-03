package com.project.flutter_backend_desktop.servicio.interfaz;

import com.project.flutter_backend_desktop.modelo.Cliente;
import com.project.flutter_backend_desktop.modelo.Record.ClienteAutoDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IClienteServicio {

    List<Cliente> listarCli();
    Cliente guardarCli(Cliente cliente);
    Cliente actualizarCli(Cliente cliente, Integer idCliente);
    void eliminarCli(Cliente cliente);
    Cliente buscarClientePorId(Integer idCliente);

    List<Cliente> listarFrecuentes();

    List<Cliente> filtrarClientes(
            String nombre,
            String telefono,
            Integer cedula
    );

    List<ClienteAutoDTO> autocompleteClientes(String nombreCompleto);

    Page<Cliente> filtrarClientesPaginados(
            String nombre,
            Integer cedula,
            String correo,
            String telefono,
            String barrio,
            String direccion,
            Integer page,
            Integer size,
            String sortBy,
            String direction
    );
}
