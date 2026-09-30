package com.lucaslleonardo.magaluaws.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucaslleonardo.magaluaws.model.entity.MensagemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
@RequiredArgsConstructor
public class SqsService {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;
    //converte objeto java -> json ou contrario

    @Value("${AWS_SQS_QUEUE_URL}")
    private String queueUrl;

    public void enviarMensagem(MensagemEntity mensagem) throws JsonProcessingException {

        String mensagemJson = objectMapper.writeValueAsString(mensagem);
        //transforma o json em text

        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(mensagemJson)
                .build();

        sqsClient.sendMessage(request);

    }
}
