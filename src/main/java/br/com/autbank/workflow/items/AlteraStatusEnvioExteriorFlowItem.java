package br.com.autbank.workflow.items;

import arch.pattern.workflow2.flow.FlowItem;
import br.com.autbank.repositories.BancoRepository;
import br.com.autbank.workflow.contexts.BancoContext;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;

@Named
@AllArgsConstructor
public class AlteraStatusEnvioExteriorFlowItem extends FlowItem<Void, BancoContext, Void> {

    private BancoRepository bancoRepository;

    @Override
    protected Void doExecute(Void unused, BancoContext bancoContext) throws Exception {
        bancoRepository.atualizaStatusEnvioExterior(bancoContext.getIdRemessa());
        return null;
    }
}