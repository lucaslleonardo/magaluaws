package com.lucaslleonardo.magaluaws.controller;

import com.lucaslleonardo.magaluaws.dto.dtoPatchRequest.UsuarioPatchRequest;
import com.lucaslleonardo.magaluaws.dto.dtoPostRequest.UsuarioPostRequest;
import com.lucaslleonardo.magaluaws.dto.dtoResponse.UsuarioResponse;
import com.lucaslleonardo.magaluaws.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@Validated
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioResponse save(@Valid @RequestBody UsuarioPostRequest usuarioPostRequest){
        return usuarioService.save(usuarioPostRequest);
    }

    public void update(@Valid @RequestBody UsuarioPatchRequest usuarioPatchRequest, @PathVariable Long id){
         usuarioService.update(usuarioPatchRequest, id);
    }

    public void delete(@PathVariable Long id){
        usuarioService.delete(id);
    }
}
