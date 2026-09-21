package com.lucaslleonardo.magaluaws.model.entity;


import com.lucaslleonardo.magaluaws.model.roles.StatusMensagem;
import com.lucaslleonardo.magaluaws.model.roles.TipoMensagem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "Mensagem")
public class MensagemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String destinatario;

    @Column(nullable = false)
    private String mensagem;

    @Column(nullable = false)
    private LocalDateTime dataEnvio;

    @Column(nullable = false)
    private StatusMensagem statusMensagem;

    @Column(nullable = false)
    private TipoMensagem tipoMensagem;

    @ManyToOne
    @JoinColumn(name= "usuario_id")
    private UsuarioEntity usuario;
}
