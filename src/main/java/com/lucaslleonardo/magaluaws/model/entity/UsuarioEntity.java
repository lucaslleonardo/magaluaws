package com.lucaslleonardo.magaluaws.model.entity;

import com.lucaslleonardo.magaluaws.model.roles.Cargos;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "Usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Cargos cargo;

    @OneToMany(mappedBy = "usuario")
    private List<MensagemEntity> mensagens;
}
