package com.project.flutter_backend_desktop.modelo;

import com.project.flutter_backend_desktop.modelo.Enum.ERol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRol;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private ERol name;
}
