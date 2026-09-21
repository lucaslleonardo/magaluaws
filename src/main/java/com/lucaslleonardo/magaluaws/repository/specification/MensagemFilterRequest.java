package com.lucaslleonardo.magaluaws.repository.specification;

import com.lucaslleonardo.magaluaws.model.roles.StatusMensagem;
import com.lucaslleonardo.magaluaws.model.roles.TipoMensagem;
import lombok.*;

import java.math.BigDecimal;

@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MensagemFilterRequest {

    private BigDecimal valor;
    private StatusMensagem status;
    private TipoMensagem tipo;

}
