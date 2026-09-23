package com.lucaslleonardo.magaluaws.controller;

import com.lucaslleonardo.magaluaws.dto.dtoPatchRequest.UsuarioPatchRequest;
import com.lucaslleonardo.magaluaws.dto.dtoPostRequest.UsuarioPostRequest;
import com.lucaslleonardo.magaluaws.dto.dtoResponse.UsuarioResponse;
import com.lucaslleonardo.magaluaws.service.UsuarioService;
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

@RestController
@RequestMapping("/usuario")
@Validated
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Usuario", description = "Operações relacionadas ao usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra o usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Usuario ja cadastrado com email"),
            @ApiResponse(responseCode = "500", description = "Erro ao cadastrar usuario")
    })
    public UsuarioResponse save(@Valid @RequestBody UsuarioPostRequest usuarioPostRequest){
        log.info("Requisicao para criar usuario");
        return usuarioService.save(usuarioPostRequest);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualiza senha do usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Usuario não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar a senha")
    })
    public void update(@Valid @RequestBody UsuarioPatchRequest usuarioPatchRequest, @PathVariable Long id){
        log.info("Requisicao para atualizar usuario");
        usuarioService.update(usuarioPatchRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Deleta usuario")
    @ApiResponse(responseCode = "404", description = "Usuario nao encontrado")
    public void delete(@PathVariable Long id){
        log.info("Requisicao para deletar usuario");
        usuarioService.delete(id);
    }
}
