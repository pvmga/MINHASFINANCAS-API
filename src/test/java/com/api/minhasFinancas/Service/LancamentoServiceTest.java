package com.api.minhasFinancas.Service;

import com.api.minhasFinancas.Exception.RegraNegocioException;
import com.api.minhasFinancas.Modelo.Entity.Lancamento;
import com.api.minhasFinancas.Modelo.Enums.StatusLancamentoEnums;
import com.api.minhasFinancas.Modelo.Repository.LancamentoRepository;
import com.api.minhasFinancas.Modelo.Repository.LancamentoRepositoryTest;
import com.api.minhasFinancas.Service.Impl.LancamentoServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith( SpringExtension.class )
@ActiveProfiles("test") // irá procurar nosso application de test
//Test Unitário
public class LancamentoServiceTest {

    @SpyBean // classe que estamos testando, ele irá chamar os métodos reais da camada service.
    LancamentoServiceImpl service;

    @MockBean // simular as chamadas reais da camada reposity que temos dentro do nosso serviço.
    LancamentoRepository repository;

    @Test
    public void deveSalvarUmLancamento() {
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
        Lancamento lancamentoASalvar = LancamentoRepositoryTest.criarLancamento();
        // irá lançar uma RegraNegocioExeception quando o servico chamar o validar.
        Mockito.doThrow(RegraNegocioException.class).when(service).validar(lancamentoASalvar);

        // -- EXECUÇÃO E VERIFICAÇÃO --- //
        // Regra que verifica o tipo de erro
        Assertions.catchThrowableOfType( () ->  service.salvar(lancamentoASalvar), RegraNegocioException.class);
        // Verificando que meu repository nunca tenha chamado com essa estrutura lancamentoASalvar
        Mockito.verify(repository, Mockito.never()).save(lancamentoASalvar);
    }

    @Test
    public void deveAtualizarUmLancamento() {
        // cenário
        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
        lancamentoSalvo.setId(1l);
        lancamentoSalvo.setStatus(StatusLancamentoEnums.PENDENTE);
        
        // não irá lançar erro quando chamar o método service chamar o método de validar
        Mockito.doNothing().when(service).validar(lancamentoSalvo);

        Mockito.when(repository.save(lancamentoSalvo)).thenReturn(lancamentoSalvo);

        // execução
        service.atualizar(lancamentoSalvo);

        // verificação
        Mockito.verify(repository, Mockito.times(1)).save(lancamentoSalvo);
        
    }

    @Test
    public void deveLancarErroAoTentarAtualizarUmLancamentoQueAindaNaoFoiSalvo() {
        Lancamento lancamentoASalvar = LancamentoRepositoryTest.criarLancamento();
        // irá lançar uma RegraNegocioExeception quando o servico chamar o validar.
        
        // -- EXECUÇÃO E VERIFICAÇÃO --- //
        // Regra que verifica o tipo de erro
        Assertions.catchThrowableOfType( () ->  service.atualizar(lancamentoASalvar), NullPointerException.class);
        // Verificando que meu repository nunca tenha chamado com essa estrutura lancamentoASalvar
        Mockito.verify(repository, Mockito.never()).save(lancamentoASalvar);
    }

    @Test
    public void deveDeletarUmLancamento() {
        // cenário
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(1l);

        // execução
        service.deletar(lancamento);

        // verificação
        Mockito.verify(repository).delete(lancamento);;
    }

    @Test
    public void deveLancarErroAoTentarDeletarUmLancamentoQueAindaNaoFoiSalvo() {
        // cenário
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();

        // execução
        Assertions.catchThrowableOfType( () ->  service.deletar(lancamento), NullPointerException.class);

        // verificação
        Mockito.verify(repository, Mockito.never()).delete(lancamento);;
    }

    @Test
    public void deveFiltrarLancamentos() {
        // cenário
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(1l);

        //List<Lancamento> lista = Arrays.asList(lancamento, lancamento, lancamento);
        List<Lancamento> lista = Arrays.asList(lancamento);
        // quando chamar o repository chamando o findall passando qualquer Example, retorna a lista
        Mockito.when(repository.findAll(Mockito.any(Example.class))).thenReturn(lista);

        // execução
        List<Lancamento> resultado = service.buscar(lancamento);

        // verificação
        Assertions
            .assertThat(resultado)
            .isNotEmpty() // não será null
            .hasSize(1) // só tem um elemento
            .contains(lancamento); // espero que retorne um lancamento
    }

    @Test
    public void deveAtualizarOStatusDeUmLancamento() {
        // cenário
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(1l);
        lancamento.setStatus(StatusLancamentoEnums.PENDENTE); // não precisaria, mas colocamos pra deixar bem evidente o status inicial
        
        StatusLancamentoEnums novoStatus = StatusLancamentoEnums.EFETIVADO;
        Mockito.doReturn(lancamento).when(service).atualizar(lancamento);

        // execução
        service.atualizarStatus(lancamento, novoStatus);

        // verificações
        Assertions.assertThat(lancamento.getStatus()).isEqualTo(novoStatus);
        Mockito.verify(service).atualizar(lancamento);
    }

    @Test
    public void deveObterUmLancamentoPorID() {
        
        // cenário
        Long id = 1l;
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(lancamento));

        // execução
        Optional<Lancamento> resultado = service.obterPorId(id);
        
        // verificação
        Assertions.assertThat(resultado.isPresent()).isTrue();

    }

    @Test
    public void deveRetornarVazioQuandoOLancamentoNaoExiste() {
        
        // cenário
        Long id = 1l;
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        // execução
        Optional<Lancamento> resultado = service.obterPorId(id);
        
        // verificação
        Assertions.assertThat(resultado.isPresent()).isFalse();

    }

    @Test
    public void develancarErrosAoValidarUmLancamento() {
        Lancamento lancamento = new Lancamento();

        // capturando o erro.
        Throwable erro = Assertions.catchThrowable( () -> service.validar(lancamento) );
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe uma Descrição válida.");
        lancamento.setDescricao("Salário");

        /*erro = Assertions.catchThrowable( () -> service.validar(lancamento) );
        Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Usuário.");
        lancamento.setUsuario(new Usuario());
        lancamento.getUsuario().setId(1l);*/

    }
}
