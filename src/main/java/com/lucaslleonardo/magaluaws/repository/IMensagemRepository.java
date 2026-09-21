package com.lucaslleonardo.magaluaws.repository;

import com.lucaslleonardo.magaluaws.model.entity.MensagemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IMensagemRepository extends JpaRepository<MensagemEntity, Long>, JpaSpecificationExecutor<MensagemEntity> {

}
