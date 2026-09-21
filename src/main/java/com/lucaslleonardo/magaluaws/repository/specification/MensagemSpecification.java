package com.lucaslleonardo.magaluaws.repository.specification;

import com.lucaslleonardo.magaluaws.model.entity.MensagemEntity;
import com.lucaslleonardo.magaluaws.model.roles.StatusMensagem;
import com.lucaslleonardo.magaluaws.model.roles.TipoMensagem;
import org.springframework.data.jpa.domain.Specification;

public class MensagemSpecification {


    public static Specification<MensagemEntity> statusMensagem(StatusMensagem statusMensagem){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), statusMensagem));
    }

    public static Specification<MensagemEntity>  tipoMensagem(TipoMensagem tipoMensagem){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("tipo"), tipoMensagem));
    }


}
