package com.api.minhasFinancas.Modelo.Repository;

import com.api.minhasFinancas.Modelo.Entity.Lancamento;
import com.api.minhasFinancas.Modelo.Enums.StatusLancamentoEnums;
import com.api.minhasFinancas.Modelo.Enums.TipoLancamentoEnums;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@ExtendWith( SpringExtension.class )
@ActiveProfiles("test") // irá procurar nosso application de test
@DataJpaTest // Será aberto transação e no fim dará rollback. Não precisaremos realizar deleteAll()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//Test Integração
public class LancamentoRepositoryTest {

    @Autowired
    LancamentoRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test // Teste de salvar lançamento
    public void deveSalvarUmLancamento() {
        // cenário
        Lancamento lancamento = criarLancamento();

        // acao
        lancamento = repository.save(lancamento);

        assertThat(lancamento.getId()).isNotNull();
    }

    @Test // Teste de deletar lançamento
    public void deveDeletarUmLancamento() {
        Lancamento lancamento = devePersistirUmLancamento();

        lancamento = entityManager.find(Lancamento.class, lancamento.getId()); // Pesquisando se existe um usuário com o id passado por parametro

        repository.delete(lancamento); // deletando o usuário encontrado

        Lancamento lancamentoInexistente = entityManager.find(Lancamento.class, lancamento.getId()); // Verificando se existe ainda o registro
        assertThat(lancamentoInexistente).isNull(); // Validando
    }

    @Test
    public void deveAtualizarUmLancamento() {
        Lancamento lancamento = devePersistirUmLancamento(); // criando lançamento

        // alterando os dados
        lancamento.setAno(2018);
        lancamento.setDescricao("Teste Atualizar");
        lancamento.setStatus(StatusLancamentoEnums.CANCELADO);
        repository.save(lancamento); // salvando com as alterações realizadas

        // buscando o lançamento na base
        Lancamento lancamentoAtualizado = entityManager.find(Lancamento.class, lancamento.getId());

        // verificando se o que foi alterado realmente está aplicado no lançamento salvo
        assertThat(lancamentoAtualizado.getAno()).isEqualTo(2018);
        assertThat(lancamentoAtualizado.getDescricao()).isEqualTo("Teste Atualizar");
        assertThat(lancamentoAtualizado.getStatus()).isEqualTo(StatusLancamentoEnums.CANCELADO);
    }

    @Test
    public void deveBuscarUmLancamentoPorId() {
        Lancamento lancamento = devePersistirUmLancamento(); // criando lançamento

        Optional<Lancamento> lancamentoEncontrado = repository.findById(lancamento.getId());

        assertThat(lancamentoEncontrado.isPresent()).isTrue();
    }


    private Lancamento devePersistirUmLancamento() {
        Lancamento lancamento = criarLancamento();
        entityManager.persist(lancamento); // salvando usuário
        return lancamento;
    }

    public static Lancamento criarLancamento() {
        return Lancamento.builder()
                .ano(2019)
                .mes(1)
                .descricao("Lancamento qualquer")
                .valor(BigDecimal.valueOf(10))
                .tipo(TipoLancamentoEnums.RECEITA)
                .status(StatusLancamentoEnums.PENDENTE)
                .dataCadastro(LocalDateTime.now(ZoneId.of("UTC"))).build();
    }
}
