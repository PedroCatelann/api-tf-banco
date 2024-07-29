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

        bancoContext.setInformacoesTransferenciaValor(informacoesTransferencia.getValor());

        bancoContext.setInformacoesTransferenciaTitularCred(informacoesTransferencia.getTitularCred());
        bancoContext.setInformacoesTransferenciaContaCred(informacoesTransferencia.getNroContaCred());

        bancoContext.setInformacoesTransferenciaTitularDeb(informacoesTransferencia.getTitularDeb());
        bancoContext.setInformacoesTransferenciaContaDeb(informacoesTransferencia.getNroContaDeb());

        bancoRepository.registraTransferencia(informacoesTransferencia.getNroContaDeb(), informacoesTransferencia.getValor(), informacoesTransferencia.getIdRemessa());
        return null;
    }
}
