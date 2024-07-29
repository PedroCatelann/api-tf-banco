package br.com.autbank.workflow.items;

import arch.common.http.body.HttpBodyHandlers;
import arch.pattern.workflow2.flow.FlowItem;
import br.com.autbank.BancoConstants;
import br.com.autbank.workflow.contexts.BancoContext;
import core.autogen.models.InformacoesTransferencia;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@Named
@Slf4j
@AllArgsConstructor
public class BuscaRemessaFlowItem extends FlowItem<InformacoesTransferencia, BancoContext, InformacoesTransferencia> {
    private HttpClient httpClient;
    @Override
    protected InformacoesTransferencia doExecute(InformacoesTransferencia informacoesTransferencia, BancoContext bancoContext) throws Exception {
        bancoContext.setIdRemessaExiste(false);
        log.info(httpClient.toString());
        var url = BancoConstants.DEFAULT_URI_REMESSA + bancoContext.getIdRemessa();
        log.info(url);

        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("content-type", "application/json")
                .GET()
                .build();

        var response = httpClient.send(request, HttpBodyHandlers.ofCodec());

        if(response.statusCode() == 200) {
            log.info("ID REMESSA ENCONTRADO, SEGUINDO COM O PROCESSO");
            bancoContext.setIdRemessaExiste(true);

            return informacoesTransferencia;
        }

        log.error("ID DA REMESSA NÃO ENCONTRADO");
        throw new Exception("ID DA REMESSA NÃO ENCONTRADO");
    }
}
