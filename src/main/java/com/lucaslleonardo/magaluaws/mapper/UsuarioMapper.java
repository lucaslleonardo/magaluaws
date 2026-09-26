package com.lucaslleonardo.magaluaws.mapper;

import com.lucaslleonardo.magaluaws.dto.dtoPatchRequest.UsuarioPatchRequest;
import com.lucaslleonardo.magaluaws.dto.dtoPostRequest.UsuarioPostRequest;
import com.lucaslleonardo.magaluaws.dto.dtoResponse.UsuarioResponse;
import com.lucaslleonardo.magaluaws.model.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mensagens", ignore = true)
    UsuarioEntity toEntity(UsuarioPostRequest usuarioPostRequest);

    UsuarioResponse toResponse(UsuarioEntity usuarioEntity);

    //erro mapeamento
//    void update(UsuarioPatchRequest usuarioPatchRequest, @MappingTarget UsuarioEntity usuarioEntity);
}
