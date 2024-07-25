package br.com.autbank.workflow.items;

import arch.pattern.workflow2.flow.FlowItem;
import br.com.autbank.repositories.BancoRepository;
import br.com.autbank.workflow.contexts.BancoContext;
import core.autogen.models.InformacoesTransferencia;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Named
@AllArgsConstructor
@Slf4j
public class InsereTransferenciaFlowItem extends FlowItem<InformacoesTransferencia, BancoContext, Void> {
    private final BancoRepository bancoRepository;

    @Override
    protected Void doExecute(InformacoesTransferencia informacoesTransferencia, BancoContext bancoContext) throws Exception {

        bancoRepository.atualizaSaldo(bancoContext.getContaDebSaldo().subtract(informacoesTransferencia.getValor()), informacoesTransferencia.getTitularDeb(), informacoesTransferencia.getNroContaDeb());
        bancoRepository.atualizaSaldo(bancoContext.getContaCredSaldo().add(informacoesTransferencia.getValor()), informacoesTransferencia.getTitularCred(), informacoesTransferencia.getNroContaCred());
        bancoRepository.registraTransferencia(informacoesTransferencia.getNroContaDeb(), informacoesTransferencia.getValor(), informacoesTransferencia.getIdRemessa());
        return null;
    }
}
