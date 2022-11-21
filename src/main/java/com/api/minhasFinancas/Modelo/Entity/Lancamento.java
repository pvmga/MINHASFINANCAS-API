package com.api.minhasFinancas.Modelo.Entity;

import com.api.minhasFinancas.Modelo.Enums.StatusLancamentoEnums;
import com.api.minhasFinancas.Modelo.Enums.TipoLancamentoEnums;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table( name = "lancamento", schema = "financas" )
@Builder // para facilitar a criação de usuário. Jeito padrão seria toda vez dar um setNome, setEmail..
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "mes")
    private Integer mes;

    @Column(name = "ano")
    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "data_cadastro")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataCadastro;

    @Column(name = "tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoLancamentoEnums tipo;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusLancamentoEnums status;

}
