package com.lucaslleonardo.magaluaws.controller;

import com.lucaslleonardo.magaluaws.dto.dtoPostRequest.MensagemPostRequest;
import com.lucaslleonardo.magaluaws.dto.dtoResponse.MensagemResponse;
import com.lucaslleonardo.magaluaws.repository.specification.MensagemFilterRequest;
import com.lucaslleonardo.magaluaws.service.MensagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Slf4j
@RestController
@RequestMapping("/mensagem")
@RequiredArgsConstructor
@Tag(name = "Mensagens", description = "Operações relacionadas as mensagens")
public class MensagemController {

    private final MensagemService mensagemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria uma mensagem nova")
    @ApiResponse(responseCode = "500", description = "Erro ao enviar mensagem")
    public MensagemResponse save(@Valid @RequestBody MensagemPostRequest mensagemPostRequest) {
        log.info("Requisição para enviar nova mensagem");
        return mensagemService.save(mensagemPostRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retorna uma mensagem")
    @ApiResponse(responseCode = "404", description = "Mensagem não encontrada")
    public MensagemResponse findById(@PathVariable Long id) {
        log.info("Requisição para encontrar mensagem");
        return mensagemService.getConsulta(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retorna todas mensagens")
    public List<MensagemResponse> getAll(MensagemFilterRequest filterRequest) {
        log.info("Requisição para retornar varias mensagens + filtro");
        return mensagemService.getMensagens(filterRequest);
    }

    @PutMapping("/cancel/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Altera o status da mensagem para CANCELADA")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Mensagem nao encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro ao alterar status da mensagem")
    })
    public void alterarStatus(@PathVariable Long id) {
        log.info("Requisição para alterar status da Mensagem");
        mensagemService.cancel(id);
    }

}
