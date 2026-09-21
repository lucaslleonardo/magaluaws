package com.lucaslleonardo.magaluaws.dto.dtoPostRequest;

import com.lucaslleonardo.magaluaws.model.roles.Cargos;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UsuarioPostRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String telefone;

    @NotBlank
    private String password;

    @NotNull
    private Cargos cargo;
}
