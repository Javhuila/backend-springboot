package com.project.flutter_backend_desktop.modelo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.flutter_backend_desktop.modelo.Enum.EstadoProducto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString()
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idProduct;

    @Column(nullable = true, unique = true)
    String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    String nombre;
    String contenido;

    @Positive(message = "El precio debe ser mayor a 0")
    Double precio;
    String marca;
    String fotoUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoProducto estado;

    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    private LocalDateTime fechaActualizacion;

    private Integer totalVendido = 0;

    @NotNull(message = "Debe seleccionar una categoría")
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @NotNull(message = "Debe seleccionar una clasificacion")
    @ManyToOne
    @JoinColumn(name = "clasificacion_id")
    private Clasificacion clasificacion;

    @OneToMany(mappedBy = "productoOrigen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromoBonoProduct> bonificaciones = new ArrayList<>();
}