package com.api.minhasFinancas.Modelo.Repository;

import com.api.minhasFinancas.Modelo.Entity.Usuario;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

// @ExtendWith( SpringExtension.class ) // Extend já está instanciado no @DataJpaTest
@ActiveProfiles("test") // irá procurar nosso application de test
@DataJpaTest // Será aberto transação e no fim dará rollback. Não precisaremos realizar deleteAll()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//Test Integração
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    TestEntityManager entityManager; // Utilizado apenas para testar. Spring JPA fornece

    @Test
    public void deveVerificarAExistenciaDeUmEmail() {
        // cenário
        Usuario usuario = criarUsuario();
        //repository.save(usuario);
        entityManager.persist(usuario); // ao usar entityManager a classe passada por parametro não pode ter id se não lança um erro

        // ação / execução
        boolean result = repository.existsByEmail("usuario@email.com");

        // verificação
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
        // cenário
        //repository.deleteAll();

        // ação / execução
        boolean result = repository.existsByEmail("usuario@email.com");

        // verificação
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void devePersistirUmUsuarioNaBaseDeDados() {
        // cenário
        Usuario usuario = criarUsuario();

        // acao
        Usuario usuarioSalvo = repository.save(usuario);

        // verificacao
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
    }

    @Test
    public void deveBuscarUmUsuarioPorEmail() {
        // cenário
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario); // ao usar entityManager a classe passada por parametro não pode ter id se não lança um erro

        // verificacao
        Optional<Usuario> result = repository.findByEmail("usuario@email.com");

        Assertions.assertThat( result.isPresent() ).isTrue();
    }

    @Test
    public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoexisteNaBase() {

        // verificacao
        Optional<Usuario> result = repository.findByEmail("usuario@email.com");

        Assertions.assertThat( result.isPresent() ).isFalse();
    }

    public static Usuario criarUsuario() {
        return Usuario
                .builder()
                .nome("usuario")
                .email("usuario@email.com")
                .senha("senha")
                .build();
    }
}
