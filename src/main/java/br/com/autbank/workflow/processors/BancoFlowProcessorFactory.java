package br.com.autbank.workflow.processors;

import arch.context.annotation.Bean;
import arch.context.annotation.Factory;
import arch.pattern.workflow2.flow.FlowBuilder;
import arch.pattern.workflow2.flow.FlowProcessor;
import br.com.autbank.workflow.contexts.BancoContext;
import br.com.autbank.workflow.items.InsereEnvioExteriorFlowitem;
import core.autogen.models.InformacoesEnvioExterior;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

@Factory
@AllArgsConstructor
public class BancoFlowProcessorFactory {

    private InsereEnvioExteriorFlowitem insereEnvioExteriorFlowitem;

    @Bean
    @Singleton
    public FlowProcessor<InformacoesEnvioExterior, BancoContext, Void> insereEnvioExterior() {
        return new FlowBuilder<BancoContext>()
                .step(insereEnvioExteriorFlowitem)
                .build();
    }
}
