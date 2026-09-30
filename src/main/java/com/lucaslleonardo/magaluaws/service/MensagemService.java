package com.lucaslleonardo.magaluaws.service;

import com.lucaslleonardo.magaluaws.dto.dtoPostRequest.MensagemPostRequest;
import com.lucaslleonardo.magaluaws.dto.dtoResponse.MensagemResponse;
import com.lucaslleonardo.magaluaws.exception.ErroAlterarStatusMensagemException;
import com.lucaslleonardo.magaluaws.exception.ErroAoEnviarMensagemException;
import com.lucaslleonardo.magaluaws.exception.MensagemNaoEncontradaException;
import com.lucaslleonardo.magaluaws.mapper.MensagemMapper;
import com.lucaslleonardo.magaluaws.model.entity.MensagemEntity;
import com.lucaslleonardo.magaluaws.model.roles.StatusMensagem;
import com.lucaslleonardo.magaluaws.repository.IMensagemRepository;
import com.lucaslleonardo.magaluaws.repository.specification.MensagemFilterRequest;
import com.lucaslleonardo.magaluaws.repository.specification.MensagemSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MensagemService {

    private final IMensagemRepository mensagemRepository;
    private final MensagemMapper mensagemMapper;
    private final SqsService sqsService;

    public MensagemResponse save(MensagemPostRequest mensagemPostRequest){

        log.info("Inicia o processo de save de mensagem");
        MensagemEntity mensagem =  mensagemMapper.toEntity(mensagemPostRequest);

        log.info("Adiciona o status AGENDADA na mensagem");
        mensagem.setStatusMensagem(StatusMensagem.AGENDADA);

        try {
            log.info("Salva a mensagem");
            MensagemEntity mensagemEntity = mensagemRepository.save(mensagem);

            log.info("Envia mensagem pro sqs");
            sqsService.enviarMensagem(mensagemEntity);
            return mensagemMapper.toResponse(mensagemEntity);
        }catch(Exception e){
            log.error("Erro ao enviar mensagem", e);
            throw new ErroAoEnviarMensagemException("Erro ao enviar mensagem", e);
        }
    }

    public MensagemResponse getConsulta(long id){
        log.info("Procura a mensagem de id {}", mensagemRepository.findById(id).get().getId());
        MensagemEntity mensagemEntity = mensagemRepository.findById(id)
                .orElseThrow(() -> new MensagemNaoEncontradaException("Mensagem nao encontrada"));

        log.info("Consulta a mensagem {}", mensagemEntity);
        return mensagemMapper.toResponse(mensagemEntity);
    }

    public List<MensagemResponse> getMensagens(MensagemFilterRequest filter){

        log.info("Procura a mensagem de filter baseada nos filtros");
        Specification<MensagemEntity> specification = null;

        if(filter.getStatus() != null){
            specification = MensagemSpecification.statusMensagem(filter.getStatus());
        }

        if(filter.getTipo() != null){
            Specification<MensagemEntity> tipoSpecification = MensagemSpecification.tipoMensagem(filter.getTipo());

            if(specification == null){
                specification = tipoSpecification;
            }else{
                specification = specification.and(tipoSpecification);
            }
        }

        return mensagemRepository.findAll(specification)
                .stream()
                .map(mensagemMapper::toResponse)
                .toList();

    }

    public void cancel(long id){

        log.info("Procura a mensagem de id {}", id);
        MensagemEntity mensagemEntity = mensagemRepository.findById(id)
                .orElseThrow(() -> new MensagemNaoEncontradaException("Mensagem nao encontrada"));

        log.info("Verifica se a mensagem tem o status AGENDADA");
        if(mensagemEntity.getStatusMensagem() == StatusMensagem.AGENDADA){
            log.info("Altera o status para CANCELADA");
            mensagemEntity.setStatusMensagem(StatusMensagem.CANCELADA);
        }else{
            log.error("Erro ao atualizar a mensagem");
            throw new ErroAlterarStatusMensagemException("Erro ao alterar status");
        }

        log.info("Salva as alteracoes");
        mensagemRepository.save(mensagemEntity);
    }
}
