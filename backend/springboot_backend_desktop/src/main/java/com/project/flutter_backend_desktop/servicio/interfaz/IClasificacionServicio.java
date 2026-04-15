package com.project.flutter_backend_desktop.servicio.interfaz;

import com.project.flutter_backend_desktop.modelo.Clasificacion;

import java.util.List;

public interface IClasificacionServicio {

    List<Clasificacion> listarClas();
    Clasificacion guardarClas(Clasificacion clasificacion);
    Clasificacion actualizarClas(Clasificacion clasificacion, Integer idClasificacion);
    void eliminarClas(Clasificacion clasificacion);
    Clasificacion buscarClasificacionPorId(Integer idClasificacion);

//    String buscarClasificacion();
}
