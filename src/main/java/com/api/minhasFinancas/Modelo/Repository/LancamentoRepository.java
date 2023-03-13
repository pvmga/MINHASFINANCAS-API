package com.api.minhasFinancas.Modelo.Repository;

import com.api.minhasFinancas.Modelo.Entity.Lancamento;
import com.api.minhasFinancas.Modelo.Enums.TipoLancamentoEnums;
import com.api.minhasFinancas.Modelo.Enums.StatusLancamentoEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
    @Query( value = " select sum(l.valor) from Lancamento l join l.usuario u where u.id = :idUsuario and l.tipo =:tipo and l.status = :status" )
    BigDecimal obterSaldoPorTipoLancamentoEUsuarioEStatus( 
        @Param("idUsuario") Long idUsuario,
        @Param("tipo") TipoLancamentoEnums tipo,
        @Param("status") StatusLancamentoEnums status );

}
