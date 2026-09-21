package com.lucaslleonardo.magaluaws.dto.dtoResponse;

import com.lucaslleonardo.magaluaws.model.roles.StatusMensagem;
import com.lucaslleonardo.magaluaws.model.roles.TipoMensagem;

import java.time.LocalDateTime;

public record MensagemResponse(StatusMensagem status, TipoMensagem tipoMensagem, LocalDateTime dataEnvio, String destinatario, String mensagem) {
}
