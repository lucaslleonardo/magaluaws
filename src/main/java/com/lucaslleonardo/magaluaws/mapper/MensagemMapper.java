package com.lucaslleonardo.magaluaws.mapper;

import com.lucaslleonardo.magaluaws.dto.dtoPostRequest.MensagemPostRequest;
import com.lucaslleonardo.magaluaws.dto.dtoResponse.MensagemResponse;
import com.lucaslleonardo.magaluaws.model.entity.MensagemEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MensagemMapper {

    MensagemEntity toEntity(MensagemPostRequest mensagemPostRequest);

    MensagemResponse toResponse(MensagemEntity mensagemEntity);

    List<MensagemResponse> toResponseList(List<MensagemEntity> mensagemEntityList);


}
