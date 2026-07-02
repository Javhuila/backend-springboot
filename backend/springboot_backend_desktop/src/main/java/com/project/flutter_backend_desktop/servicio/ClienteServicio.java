package com.project.flutter_backend_desktop.servicio;

import ch.qos.logback.core.net.server.Client;
import com.project.flutter_backend_desktop.modelo.Cliente;
import com.project.flutter_backend_desktop.modelo.Product;
import com.project.flutter_backend_desktop.modelo.Record.ClienteAutoDTO;
import com.project.flutter_backend_desktop.repositorio.ClienteRepositorio;
import com.project.flutter_backend_desktop.servicio.interfaz.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ClienteServicio  implements IClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public List<Cliente> listarCli() {
        return clienteRepositorio.findAll();
    }

    @Override
    public Cliente guardarCli(Cliente cliente) {
        String nombreCompleto = cliente.getNombre() + " " + cliente.getApellido();
        cliente.setNombreCompleto(nombreCompleto);

        return clienteRepositorio.save(cliente);
    }

    @Override
    public Cliente actualizarCli(Cliente cliente, Integer idCliente) {
        Cliente existente = clienteRepositorio.findById(idCliente).orElseThrow();

        existente.setNombre(cliente.getNombre());
        existente.setApellido(cliente.getApellido());

        String nombreCompleto = cliente.getNombre() + " " + cliente.getApellido();
        existente.setNombreCompleto(nombreCompleto);
        existente.setFrecuente(cliente.getFrecuente());
        existente.setCedula(cliente.getCedula());
        existente.setCorreo(cliente.getCorreo());
        existente.setTelefono(cliente.getTelefono());
        existente.setBarrio(cliente.getBarrio());
        existente.setDireccion(cliente.getDireccion());
        return clienteRepositorio.save(existente);
    }

    @Override
    public void eliminarCli(Cliente cliente) {
        clienteRepositorio.delete(cliente);
    }

    @Override
    public Cliente buscarClientePorId(Integer idCliente) {
        Cliente cliente = clienteRepositorio.findById(idCliente).orElseThrow(() ->
                new RuntimeException("Cliente no encontrado")
        );
        return cliente;
    }

    @Override
    public List<Cliente> listarFrecuentes() {
        return clienteRepositorio.findByFrecuenteTrue();
    }

    @Override
    public List<Cliente> filtrarClientes(String nombre, String telefono, Integer cedula) {

        Specification<Cliente> spec = (root, query, cb) -> cb.conjunction();

        // nombre, apellido o nombreCompleto
        if (StringUtils.hasText(nombre)) {
            spec = spec.and((root, query, cb) ->
                    cb.or(
                            cb.like(root.get("nombre"), "%" + nombre + "%"),
                            cb.like(root.get("apellido"), "%" + nombre + "%"),
                            cb.like(root.get("nombreCompleto"), "%" + nombre + "%")
                    ));
        }

        if (telefono != null) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("telefono"), "%" + telefono + "%"));
        }

        if (cedula != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("cedula"), cedula));
        }

        return clienteRepositorio.findAll(spec);
    }

    @Override
    public List<ClienteAutoDTO> autocompleteClientes(String nombreCompleto) {
        return clienteRepositorio
                .findTop10ByNombreCompletoContainingIgnoreCase(nombreCompleto)
                .stream()
                .map(c -> new ClienteAutoDTO(
                        c.getIdCliente(),
                        c.getNombreCompleto(),
                        c.getCedula(),
                        c.getTelefono()
                ))
                .toList();
    }

    @Override
    public Page<Cliente> filtrarClientesPaginados(
            String nombre,
            Integer cedula,
            String telefono,
            String correo,
            String barrio,
            String direccion,
            Integer page,
            Integer size,
            String sortBy,
            String direction
    ) {
        Specification<Cliente> spec = (root, query, cb) -> cb.conjunction();
        // nombre, apellido o nombreCompleto
        if (nombre != null) {
            spec = spec.and((root, query, cb) ->
                    cb.or(
                            cb.like(root.get("nombre"), "%" + nombre + "%"),
                            cb.like(root.get("apellido"), "%" + nombre + "%"),
                            cb.like(root.get("nombreCompleto"), "%" + nombre + "%")
                    ));
        }

        if (telefono != null) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("telefono"), "%" + telefono + "%"));
        }

        if (cedula != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("cedula"), cedula));
        }

        if (correo != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("correo"), correo));
        }

        if (barrio != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("barrio"), barrio));
        }

        if (direccion != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("direccion"), direccion));
        }
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return clienteRepositorio.findAll(spec, pageable);
    }
}
