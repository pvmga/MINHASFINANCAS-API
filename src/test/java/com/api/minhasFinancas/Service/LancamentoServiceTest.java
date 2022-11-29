package com.api.minhasFinancas.Service;

import com.api.minhasFinancas.Modelo.Entity.Lancamento;
import com.api.minhasFinancas.Modelo.Enums.StatusLancamentoEnums;
import com.api.minhasFinancas.Modelo.Repository.LancamentoRepository;
import com.api.minhasFinancas.Modelo.Repository.LancamentoRepositoryTest;
import com.api.minhasFinancas.Service.Impl.LancamentoServiceImpl;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith( SpringExtension.class )
@ActiveProfiles("test") // irá procurar nosso application de test
//Test Unitário
public class LancamentoServiceTest {

    @SpyBean // classe que estamos testando, ele irá chamar os métodos reais.
    LancamentoServiceImpl service;

    @MockBean // simular as chamadas reais que temos dentro do nosso serviço.
    LancamentoRepository repository;

    @Test
    public void devaSalvarUmLancamento() {
        // cenário
        Lancamento lancamentoASalvar = LancamentoRepositoryTest.criarLancamento();
        // não irá lançar erro quando chamar o método service chamar o método de validar
        Mockito.doNothing().when(service).validar(lancamentoASalvar);

        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
        lancamentoSalvo.setId(1l);
        lancamentoSalvo.setStatus(StatusLancamentoEnums.PENDENTE);
        Mockito.when(repository.save(lancamentoASalvar)).thenReturn(lancamentoSalvo);

        // execução
        Lancamento lancamento = service.salvar(lancamentoASalvar);

        // verificação
        Assertions.assertThat(lancamento.getId()).isEqualTo(lancamentoSalvo.getId());
        Assertions.assertThat(lancamento.getStatus()).isEqualTo(StatusLancamentoEnums.PENDENTE);
    }

    @Test
    public void naoDeveSalvarLancamentoQuandoHouverErroDeValidacao() {

    }
}
