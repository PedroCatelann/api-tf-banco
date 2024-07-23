package br.com.autbank.controllers;

import arch.common.http.HttpResponse;
import arch.pattern.workflow2.flow.FlowProcessor;
import br.com.autbank.workflow.contexts.BancoContext;
import br.com.autbank.workflow.processors.BancoFlowProcessorFactory;
import core.autogen.controllers.SecuredController;
import core.autogen.models.InformacoesEnvioExterior;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class SecuredControllerImpl implements SecuredController {

    private final FlowProcessor<InformacoesEnvioExterior, BancoContext, Void> insereInformacaoFlowProcessor;

    @Override
    public HttpResponse<Void> envioExterior(EnvioExteriorRequest request) throws Exception {

        var context = BancoContext.builder()
                .valor(request.getBody().getValor())
                .build();

        return HttpResponse.ok(insereInformacaoFlowProcessor.execute(request.getBody(), context));
    }
}
