package br.com.autbank.workflow.contexts;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class BancoContext {
    BigDecimal valor;
    Integer idRemessa;
    Boolean clienteTemSaldo;
    Boolean idRemessaExiste;
}
