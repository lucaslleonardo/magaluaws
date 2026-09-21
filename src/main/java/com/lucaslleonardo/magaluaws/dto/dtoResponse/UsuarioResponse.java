package com.lucaslleonardo.magaluaws.dto.dtoResponse;

import lombok.Builder;

@Builder
public record UsuarioResponse(String email, String telefone) {
}
