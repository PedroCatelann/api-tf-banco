package br.com.autbank.workflow.items;

import arch.pattern.workflow2.flow.FlowItem;
import br.com.autbank.workflow.contexts.BancoContext;
import core.autogen.models.InformacoesTransferencia;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;

@Named
@Slf4j
public class SimulaKafkaFlowItem extends FlowItem<Void, BancoContext, Void> {
    @Override
    protected Void doExecute(Void informacoesTransferencia, BancoContext bancoContext) throws Exception {
        log.info("SIMULAÇÃO DE CHAMADA AO TÓPICO KAFKA");
        log.info("REMESSA CRIADA");
        return null;
    }
}
