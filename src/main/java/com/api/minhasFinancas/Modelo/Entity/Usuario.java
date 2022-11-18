package com.api.minhasFinancas.Modelo.Entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Table( name = "usuario" , schema = "financas")
@Builder // para facilitar a criação de usuário. Jeito padrão seria toda vez dar um setNome, setEmail..
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @Column(name = "id")
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

}
