package br.com.autbank.controllers;

import arch.common.http.HttpResponse;
import arch.pattern.workflow2.flow.FlowProcessor;
import br.com.autbank.workflow.contexts.BancoContext;
import core.autogen.controllers.TransferenciasController;
import core.autogen.models.InformacoesTransferencia;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class TransferenciaControllerImpl implements TransferenciasController {

    private final FlowProcessor<InformacoesTransferencia, BancoContext, Void> insereInformacaoFlowProcessor;
    @Override
    public HttpResponse<Void> realizaTransferencia(RealizaTransferenciaRequest request) throws Exception {
        var context = BancoContext.builder()
                .idRemessa(request.getBody().getIdRemessa())
                .valor(request.getBody().getValor())
                .build();
        return HttpResponse.ok(insereInformacaoFlowProcessor.execute(request.getBody(), context));
    }
}
