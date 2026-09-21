package com.lucaslleonardo.magaluaws.repository;

import com.lucaslleonardo.magaluaws.model.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Long>, JpaSpecificationExecutor<UsuarioEntity> {

    Optional<UsuarioEntity> findByEmail(String email);

}
