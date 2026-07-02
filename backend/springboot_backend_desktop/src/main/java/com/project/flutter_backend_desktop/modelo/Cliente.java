package com.project.flutter_backend_desktop.modelo;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idCliente;
    String nombreCompleto;

    @NotBlank(message = "El nombre es obligatorio")
    String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    String apellido;

    @Column(nullable =false)
    private Boolean frecuente = false;

    Integer cedula;
    String correo;
    String telefono;
    String barrio;
    String direccion;
}
