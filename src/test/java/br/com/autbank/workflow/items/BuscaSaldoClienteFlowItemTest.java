package br.com.autbank.workflow.items;

import br.com.autbank.repositories.BancoRepository;
import br.com.autbank.workflow.contexts.BancoContext;
import core.autogen.models.InformacoesTransferencia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class BuscaSaldoClienteFlowItemTest {

    @Mock
    private BancoRepository bancoRepository;

    @Mock
    private InformacoesTransferencia informacoesTransferencia;

    @Mock
    private BancoContext bancoContext;

    @Test
    void retornaInformacaoTransferenciaSeContaCredEDebExistem() throws Exception {
        var valorEsperado = informacoesTransferencia;
        var unit = new BuscaSaldoClienteFlowItem(bancoRepository);

        Mockito.when(informacoesTransferencia.getNroContaDeb()).thenReturn("11155");
        Mockito.when(informacoesTransferencia.getTitularDeb()).thenReturn("PEDRO CATELAN PLATAF");

        Mockito.when(informacoesTransferencia.getNroContaCred()).thenReturn("22266");
        Mockito.when(informacoesTransferencia.getTitularCred()).thenReturn("PEDRO CATELAN");

        var retorno = unit.doExecute(informacoesTransferencia, bancoContext);
        Assertions.assertEquals(valorEsperado, retorno);
    }

    @Test
    void retornaFalsoSeContaDebNaoExistir() throws Exception {
        var valorEsperado = false;
        var unit = new BuscaSaldoClienteFlowItem(bancoRepository);


        Mockito.when(informacoesTransferencia.getNroContaDeb()).thenReturn("13155");
        Mockito.when(informacoesTransferencia.getTitularDeb()).thenReturn("PEDRO CATELAN PLATAF");

        Mockito.when(informacoesTransferencia.getNroContaCred()).thenReturn("22266");
        Mockito.when(informacoesTransferencia.getTitularCred()).thenReturn("PEDRO CATELAN");

        var retorno = unit.validaSaldo(informacoesTransferencia, bancoContext);
        Assertions.assertEquals(valorEsperado, retorno);
    }

    @Test
    void retornaFalsoSeContaCredNaoExistir() throws Exception {
        var valorEsperado = false;
        var unit = new BuscaSaldoClienteFlowItem(bancoRepository);

        Mockito.when(informacoesTransferencia.getNroContaDeb()).thenReturn("11155");
        Mockito.when(informacoesTransferencia.getTitularDeb()).thenReturn("PEDRO CATELAN PLATAF");

        Mockito.when(informacoesTransferencia.getNroContaCred()).thenReturn("22466");
        Mockito.when(informacoesTransferencia.getTitularCred()).thenReturn("PEDRO CATELAsN");

        var retorno = unit.validaSaldo(informacoesTransferencia, bancoContext);
        Assertions.assertEquals(valorEsperado, retorno);
    }
}
