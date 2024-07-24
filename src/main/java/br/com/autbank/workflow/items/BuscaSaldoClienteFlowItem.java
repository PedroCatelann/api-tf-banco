package br.com.autbank.workflow.items;

import arch.pattern.workflow2.flow.FlowItem;
import br.com.autbank.repositories.BancoRepository;
import br.com.autbank.workflow.contexts.BancoContext;
import core.autogen.models.InformacoesTransferencia;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Named
@Slf4j
@AllArgsConstructor
public class BuscaSaldoClienteFlowItem extends FlowItem<InformacoesTransferencia, BancoContext, InformacoesTransferencia> {
    private BancoRepository bancoRepository;

    @Override
    protected InformacoesTransferencia doExecute(InformacoesTransferencia informacoesTransferencia, BancoContext bancoContext) throws Exception {
        var clientTemSaldo = validaSaldo(informacoesTransferencia, bancoContext);
        bancoContext.setClienteTemSaldo(clientTemSaldo);
        return informacoesTransferencia;
    }



    public boolean validaSaldo(InformacoesTransferencia informacoesTransferencia, BancoContext bancoContext) {

        var conta = bancoRepository.buscaContas(informacoesTransferencia.getTitularDeb(), informacoesTransferencia.getNroContaDeb());

        bancoContext.setIdRemessa(informacoesTransferencia.getIdRemessa());

        if (conta.getSaldo().subtract(informacoesTransferencia.getValor()).compareTo(BigDecimal.ZERO) < 0) {
            log.error("NÃO HÁ SALDO SUFICIENTE NA CONTA");
            return false;
        }

        return true;
    }
}
