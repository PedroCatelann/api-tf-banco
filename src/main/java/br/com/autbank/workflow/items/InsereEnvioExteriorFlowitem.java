package br.com.autbank.workflow.items;

import arch.pattern.workflow2.flow.FlowItem;
import br.com.autbank.repositories.BancoRepository;
import br.com.autbank.workflow.contexts.BancoContext;
import core.autogen.models.InformacoesEnvioExterior;
import jakarta.inject.Named;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;

@Named
@AllArgsConstructor
public class InsereEnvioExteriorFlowitem extends FlowItem<InformacoesEnvioExterior, BancoContext, Void> {
    private final BancoRepository repository;

    @Override
    protected Void doExecute(InformacoesEnvioExterior informacoesEnvioExterior, BancoContext bancoContext) throws Exception {

        repository.registraEnvioExterior(informacoesEnvioExterior.getIdRemessa(), informacoesEnvioExterior.getValor(), informacoesEnvioExterior.getStatus());
        return null;
    }
}
