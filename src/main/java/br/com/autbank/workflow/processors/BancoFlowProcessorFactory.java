package br.com.autbank.workflow.processors;

import arch.context.annotation.Bean;
import arch.context.annotation.Factory;
import arch.pattern.workflow2.flow.FlowBuilder;
import arch.pattern.workflow2.flow.FlowProcessor;
import br.com.autbank.workflow.contexts.BancoContext;
import br.com.autbank.workflow.items.*;
import br.com.autbank.workflow.predicate.ValidaIdRemessaESaldoFlowItem;
import core.autogen.models.InformacoesEnvioExterior;
import core.autogen.models.InformacoesTransferencia;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import static arch.pattern.workflow2.flow.FlowBuilder.ifFalse;
import static arch.pattern.workflow2.flow.FlowBuilder.ifTrue;

@Factory
@AllArgsConstructor
public class BancoFlowProcessorFactory {

    private InsereEnvioExteriorFlowitem insereEnvioExteriorFlowitem;
    private InsereTransferenciaFlowItem insereTransferenciaFlowItem;
    private InformaErroIdRemessaFlowitem informaErroIdRemessaFlowitem;
    private BuscaSaldoClienteFlowItem buscaSaldoClienteFlowItem;
    private BuscaRemessaFlowItem buscaRemessaFlowItem;
    private SimulaKafkaFlowItem simulaKafkaFlowItem;
    private AlteraStatusEnvioExteriorFlowItem alteraStatusEnvioExteriorFlowItem;
    @Bean
    @Singleton
    public FlowProcessor<InformacoesEnvioExterior, BancoContext, Void> insereEnvioExterior() {
        return new FlowBuilder<BancoContext>()
                .step(insereEnvioExteriorFlowitem)
                .build();
    }

    @Bean
    @Singleton
    public FlowProcessor<InformacoesTransferencia, BancoContext, Void> insereTransferenci() {
        return new FlowBuilder<BancoContext>()
                .step(buscaSaldoClienteFlowItem)
                .step(buscaRemessaFlowItem)
                .binary(new ValidaIdRemessaESaldoFlowItem(),
                            ifTrue(insereTransferenciaFlowItem),
                            ifFalse(informaErroIdRemessaFlowitem)
                        )
                .step(simulaKafkaFlowItem)
                .step(alteraStatusEnvioExteriorFlowItem)
                .build();
    }
}
