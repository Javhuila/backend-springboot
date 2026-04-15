package com.project.flutter_backend_desktop.controlador;

import com.project.flutter_backend_desktop.modelo.Clasificacion;
import com.project.flutter_backend_desktop.servicio.interfaz.IClasificacionServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/clasification")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:8080",
        "http://10.0.2.2:8080",
        "http://192.168.1.104:8080"
})
public class ClasificacionControlador {
    private static final Logger logger = LoggerFactory.getLogger(ProductControlador.class);

    @Autowired
    private IClasificacionServicio clasificacionServicio;

    @GetMapping("/clasificaciones")
    public List<Clasificacion> listarClasif() {
        var clasif = clasificacionServicio.listarClas();
        clasif.forEach(ap -> logger.info(ap.toString()));
        return clasif;
    }

    @PostMapping("/clasificaciones")
    public ResponseEntity<Clasificacion> guardarClasif(@RequestBody Clasificacion clasificacion) {
        Clasificacion nuevoclasificacion = clasificacionServicio.guardarClas(clasificacion);
        return ResponseEntity.ok(nuevoclasificacion);
    }

    @PutMapping("/editClasificaciones/{id}")
    public Clasificacion actualizarClasif(@PathVariable Integer id, @RequestBody Clasificacion clasificacion) {
        return clasificacionServicio.actualizarClas(clasificacion, id);
    }

    @GetMapping("/clasificaciones/{id}")
    public ResponseEntity<Clasificacion> obtenerClasificacionPorId(@PathVariable Integer id) {
        Clasificacion clasificacion = clasificacionServicio.buscarClasificacionPorId(id);
        if(clasificacion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clasificacion);
    }

    @DeleteMapping("/clasificaciones/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarClasif(@PathVariable Integer id) {
        Clasificacion clasificacion = clasificacionServicio.buscarClasificacionPorId(id);
        if (clasificacion == null) {
            return ResponseEntity.notFound().build();
        }
        clasificacionServicio.eliminarClas(clasificacion);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
