package com.lucaslleonardo.magaluaws.service;

import com.lucaslleonardo.magaluaws.dto.dtoPatchRequest.UsuarioPatchRequest;
import com.lucaslleonardo.magaluaws.dto.dtoPostRequest.UsuarioPostRequest;
import com.lucaslleonardo.magaluaws.dto.dtoResponse.UsuarioResponse;
import com.lucaslleonardo.magaluaws.exception.EmailJaUsadoException;
import com.lucaslleonardo.magaluaws.exception.ErroAoAtualizarSenha;
import com.lucaslleonardo.magaluaws.exception.ErroAoCadastrarException;
import com.lucaslleonardo.magaluaws.exception.UsuarioNaoEncontradoException;
import com.lucaslleonardo.magaluaws.mapper.UsuarioMapper;
import com.lucaslleonardo.magaluaws.model.entity.UsuarioEntity;
import com.lucaslleonardo.magaluaws.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;


    public UsuarioResponse save (UsuarioPostRequest usuarioPostRequest) {

        log.info("Verifica se o email: {} ja foi usado",usuarioPostRequest.getEmail());
        if(usuarioRepository.findByEmail(usuarioPostRequest.getEmail()).isPresent()) {
            throw new EmailJaUsadoException("Email ja cadastrado");
        }

        log.info("Cadastra o usuario com o email {}",usuarioPostRequest.getEmail());
        UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuarioPostRequest);

        try{
            UsuarioEntity usuario = usuarioRepository.save(usuarioEntity);
            return usuarioMapper.toResponse(usuario);
        } catch(Exception e){
            log.error("Erro ao cadastrar email");
            throw new ErroAoCadastrarException("Erro ao cadastrar usuario", e);
        }

    }

    public void update(UsuarioPatchRequest usuarioPatchRequest, Long id) {
        log.info("Busca o usuario com o id {}",id);
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario nao encontrado"));

        log.info("Altera a senha do usuario");
        usuarioEntity.setPassword(usuarioPatchRequest.getPassword());

        try{
            usuarioMapper.update(usuarioPatchRequest, usuarioEntity);
            usuarioRepository.save(usuarioEntity);
        } catch(Exception e){
            log.error("Erro ao atualizar a senha");
            throw new ErroAoAtualizarSenha("Erro ao atualizar senha", e);
        }
    }

    public void delete(Long id) {
        log.info("Busca o usuario com o id {}",id);
        if(usuarioRepository.findById(id).isEmpty()) {
            throw new UsuarioNaoEncontradoException("Usuario nao encontrado");
        }
        log.info("Deleta o usuario com o id {}",id);
        usuarioRepository.deleteById(id);
    }

}
