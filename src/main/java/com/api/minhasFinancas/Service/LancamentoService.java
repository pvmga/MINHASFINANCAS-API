package com.api.minhasFinancas.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.api.minhasFinancas.Modelo.Entity.Lancamento;
import com.api.minhasFinancas.Modelo.Enums.StatusLancamentoEnums;

public interface LancamentoService {

    Lancamento salvar(Lancamento lancamento);
    Lancamento atualizar(Lancamento lancamento);
    void deletar(Lancamento lancamento);
    List<Lancamento> buscar(Lancamento lancamentoFiltro);
    void atualizarStatus(Lancamento lancamento, StatusLancamentoEnums status);
    void validar(Lancamento lancamento);

    Optional<Lancamento> obterPorId(Long id);

    BigDecimal obterSaldoPorUsuario(Long id);
}
