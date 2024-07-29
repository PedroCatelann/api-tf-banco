package br.com.autbank.workflow.items;

import arch.pattern.workflow2.flow.FlowItem;
import br.com.autbank.repositories.BancoRepository;
import br.com.autbank.workflow.contexts.BancoContext;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Named
@AllArgsConstructor
@Slf4j
public class AlteraStatusEnvioExteriorFlowItem extends FlowItem<Void, BancoContext, Void> {

    private BancoRepository bancoRepository;

    @Override
    protected Void doExecute(Void unused, BancoContext bancoContext) throws Exception {

        bancoRepository.atualizaStatusEnvioExterior("LIBERADO", bancoContext.getIdRemessa());
        var valorReais = bancoContext.getInformacoesTransferenciaValor().multiply(bancoContext.getCotacaoDolar());
        log.info("VALOR EM REAIS {}", valorReais);
        // NA CONTA DE DÉBITO VAI DESCONTAR O VALOR EM REAIS (VALOR * COTACAO)
        bancoRepository.atualizaSaldo(bancoContext.getContaDebSaldo().subtract(valorReais), bancoContext.getInformacoesTransferenciaTitularDeb(), bancoContext.getInformacoesTransferenciaContaDeb());

        // NA CONTA CRÉDITO (PLATAFORMA) VAI RECEBER O VALOR EM DOLAR (20 REAIS)
        bancoRepository.atualizaSaldo(bancoContext.getContaCredSaldo().add(bancoContext.getInformacoesTransferenciaValor()), bancoContext.getInformacoesTransferenciaTitularCred(), bancoContext.getInformacoesTransferenciaContaCred());
        return null;
    }
}
