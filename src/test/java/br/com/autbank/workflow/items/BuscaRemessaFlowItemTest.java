package br.com.autbank.workflow.items;

import br.com.autbank.workflow.contexts.BancoContext;
import core.autogen.models.InformacoesTransferencia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ExtendWith(MockitoExtension.class)
class BuscaRemessaFlowItemTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private InformacoesTransferencia informacoesTransferencia;

    @Mock
    private BancoContext bancoContext;

    @Mock
    private HttpResponse<String> httpResponse;

    @Test
    void retornaObjetoSeIDDaRemessaExistir() throws Exception {
        var valorEsperado = informacoesTransferencia;
        var unit = new BuscaRemessaFlowItem(httpClient);
        Mockito.when(bancoContext.getIdRemessa()).thenReturn(20);
        Mockito.when(httpResponse.statusCode()).thenReturn(200);
        Mockito.when(httpClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        var retorno = unit.doExecute(informacoesTransferencia, bancoContext);

        Assertions.assertEquals(valorEsperado, retorno);
    }

    @Test
    void retornaExcecaoSeIDDaRemessaNaoExistir() throws Exception {

        var unit = new BuscaRemessaFlowItem(httpClient);
        Mockito.when(bancoContext.getIdRemessa()).thenReturn(50);
        Mockito.when(httpResponse.statusCode()).thenReturn(500);
        Mockito.when(httpClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class))).thenReturn(httpResponse);

        Assertions.assertThrows(Exception.class, () -> {
            unit.doExecute(informacoesTransferencia, bancoContext);
        });
    }
}
