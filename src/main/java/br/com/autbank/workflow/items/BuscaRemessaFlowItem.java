package br.com.autbank.workflow.items;

import arch.common.http.body.HttpBodyCodec;
import arch.common.http.body.HttpBodyHandlers;
import arch.pattern.workflow2.flow.FlowItem;
import br.com.autbank.BancoConstants;
import br.com.autbank.exceptions.ConexaoAPIRemessaException;
import br.com.autbank.exceptions.ResourceNotFoundException;
import br.com.autbank.model.Remessa;
import br.com.autbank.workflow.contexts.BancoContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.autogen.models.InformacoesTransferencia;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Named
@Slf4j
@AllArgsConstructor
public class BuscaRemessaFlowItem extends FlowItem<InformacoesTransferencia, BancoContext, InformacoesTransferencia> {
    private HttpClient httpClient;
    @Override
    protected InformacoesTransferencia doExecute(InformacoesTransferencia informacoesTransferencia, BancoContext bancoContext) throws Exception {
        bancoContext.setIdRemessaExiste(false);
        var url = BancoConstants.DEFAULT_URI_REMESSA + bancoContext.getIdRemessa();
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("content-type", "application/json")
                .GET()
                .build();
        log.info(url);

        try {

            var response = httpClient.send(request, HttpBodyHandlers.ofString());

            if(response.statusCode() == 200) {
                log.info("ID REMESSA ENCONTRADO, SEGUINDO COM O PROCESSO");
                ObjectMapper objectMapper = new ObjectMapper();
                Remessa remessa = objectMapper.readValue(response.body(), Remessa.class);

                log.info("COATACAO DOLAR: {}", remessa.getCotacao());

                bancoContext.setIdRemessaExiste(true);
                bancoContext.setCotacaoDolar(remessa.getCotacao());
                return informacoesTransferencia;
            }

            log.error("ID DA REMESSA NÃO ENCONTRADO");
            throw new ResourceNotFoundException("ID DA REMESSA NÃO ENCONTRADO");
        } catch (ConnectException e) {
            throw new ConexaoAPIRemessaException("Não foi possível estabelecer uma conexão com a API de remessas", e.getCause());
        }

    }
}
