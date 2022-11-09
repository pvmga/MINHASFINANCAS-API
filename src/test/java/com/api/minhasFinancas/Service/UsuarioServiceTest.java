package com.api.minhasFinancas.Service;

import com.api.minhasFinancas.Modelo.Entity.Usuario;
import com.api.minhasFinancas.Modelo.Repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    UsuarioService service;

    @Autowired
    UsuarioRepository repository;

    @Test
    public void deveValidarEmail() {
        // cenario
        repository.deleteAll();

        // acao
        service.validarEmail("email@email.com");

    }

    @Test
    public void deveLancarErroQuandoExistirEmailCadastrado() {
        // cenário
        Usuario usuario = Usuario.builder().nome("usuario").email("email@email.com").build();

        // acao
        service.validarEmail("email@email.com");
    }
}
