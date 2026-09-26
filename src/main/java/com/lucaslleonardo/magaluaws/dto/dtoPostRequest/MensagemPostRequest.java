package com.lucaslleonardo.magaluaws.dto.dtoPostRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lucaslleonardo.magaluaws.model.roles.StatusMensagem;
import com.lucaslleonardo.magaluaws.model.roles.TipoMensagem;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MensagemPostRequest {

    @NotNull
    private String destinatario;

    @NotNull
    private String mensagem;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataEnvio;

    @NotNull
    private TipoMensagem tipoMensagem;
}
