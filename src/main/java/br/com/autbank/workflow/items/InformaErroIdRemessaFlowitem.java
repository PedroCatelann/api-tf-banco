package br.com.autbank.workflow.items;

import arch.pattern.workflow2.flow.FlowItem;
import br.com.autbank.workflow.contexts.BancoContext;
import core.autogen.models.InformacoesTransferencia;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;

@Named
@Slf4j
public class InformaErroIdRemessaFlowitem extends FlowItem<InformacoesTransferencia, BancoContext, Void> {
    @Override
    protected Void doExecute(InformacoesTransferencia informacoesTransferencia, BancoContext bancoContext) throws Exception {
        if(!bancoContext.getIdRemessaExiste())
            log.error("NÃO FOI POSSÍVEL ENCONTRAR A REMESSA DE ID: {}", informacoesTransferencia.getIdRemessa());
        if(!bancoContext.getClienteTemSaldo())
            log.error("CLIENTE NÃO TEM SALDO SUFICIENTE");
        return null;
    }
}
