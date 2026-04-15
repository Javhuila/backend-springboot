package com.project.flutter_backend_desktop.servicio;

import com.project.flutter_backend_desktop.modelo.Clasificacion;
import com.project.flutter_backend_desktop.repositorio.ClasificacionRepositorio;
import com.project.flutter_backend_desktop.servicio.interfaz.IClasificacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClasificacionServicio implements IClasificacionServicio {

    @Autowired
    private ClasificacionRepositorio clasificacionRepositorio;

    @Override
    public List<Clasificacion> listarClas() {
        return clasificacionRepositorio.findAll();
    }

    @Override
    public Clasificacion buscarClasificacionPorId(Integer idClasificacion) {
        Clasificacion clasificacion = clasificacionRepositorio.findById(idClasificacion).orElse(null);
        return clasificacion;
    }

    @Override
    public Clasificacion guardarClas(Clasificacion clasificacion) {
        return clasificacionRepositorio.save(clasificacion);
    }

    @Override
    public Clasificacion actualizarClas(Clasificacion clasificacion, Integer idClasificacion) {
        Clasificacion existente = clasificacionRepositorio.findById(idClasificacion).orElseThrow();
        existente.setNombre(clasificacion.getNombre());
        return clasificacionRepositorio.save(existente);
    }

    @Override
    public void eliminarClas(Clasificacion clasificacion) {
        clasificacionRepositorio.delete(clasificacion);
    }
}
