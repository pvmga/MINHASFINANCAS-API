package com.api.minhasFinancas.Modelo.Repository;

import com.api.minhasFinancas.Modelo.Entity.Usuario;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest
@ExtendWith( SpringExtension.class )
@ActiveProfiles("test") // irá procurar nosso application de test
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Test
    public void deveVerificarAExistenciaDeUmEmail() {
        // cenário
        Usuario usuario = Usuario.builder().nome("usuario").email("usuario@email.com").build();
        repository.save(usuario);

        // ação / execução
        boolean result = repository.existsByEmail("usuario@email.com");

        // verificação
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
        // cenário
        repository.deleteAll();

        // ação / execução
        boolean result = repository.existsByEmail("usuario@email.com");

        // verificação
        Assertions.assertThat(result).isFalse();
    }
}
