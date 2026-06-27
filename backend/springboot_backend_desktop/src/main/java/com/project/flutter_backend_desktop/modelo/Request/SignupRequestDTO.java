package com.project.flutter_backend_desktop.modelo.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDTO {
    @NotBlank
//    @Size(max = 15)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String username;

    private Set<String> role;

    @NotBlank
//    @Size(max = 10)
    private String password;

    private String cedula;


}
