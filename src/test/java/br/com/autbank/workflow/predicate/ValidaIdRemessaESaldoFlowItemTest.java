package br.com.autbank.workflow.predicate;

import br.com.autbank.workflow.contexts.BancoContext;
import core.autogen.models.InformacoesTransferencia;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidaIdRemessaESaldoFlowItemTest {
    @Mock
    private BancoContext bancoContext;
    @Mock
    private InformacoesTransferencia informacoesTransferencia;

    @Test
    void retornaTrueSeTiverSaldoEIdRemessaExistir() {
        var valorEsperado = true;
        var unit = new ValidaIdRemessaESaldoFlowItem();

        Mockito.when(bancoContext.getIdRemessaExiste()).thenReturn(true);
        Mockito.when(bancoContext.getClienteTemSaldo()).thenReturn(true);

        var retorno = unit.test(informacoesTransferencia, bancoContext);
        Assertions.assertEquals(valorEsperado, retorno);

    }

    @Test
    void retornaFalseSeTiverSaldoMasRemessaNaoExistir() {
        var valorEsperado = false;
        var unit = new ValidaIdRemessaESaldoFlowItem();

        Mockito.when(bancoContext.getIdRemessaExiste()).thenReturn(false);
        Mockito.when(bancoContext.getClienteTemSaldo()).thenReturn(true);

        var retorno = unit.test(informacoesTransferencia, bancoContext);
        Assertions.assertEquals(valorEsperado, retorno);
    }

    @Test
    void retornaFalseSeRemessaExistirMasNaoTiverSaldoasdasdas() {
        var valorEsperado = false;
        var unit = new ValidaIdRemessaESaldoFlowItem();

        Mockito.lenient().when(bancoContext.getIdRemessaExiste()).thenReturn(true);
        Mockito.lenient().when(bancoContext.getClienteTemSaldo()).thenReturn(false);

        var retorno = unit.test(informacoesTransferencia, bancoContext);
        Assertions.assertEquals(valorEsperado, retorno);
    }

    @Test
    void retornaFalseSeNaoTiverNada() {
        var valorEsperado = false;
        var unit = new ValidaIdRemessaESaldoFlowItem();

        Mockito.lenient().when(bancoContext.getIdRemessaExiste()).thenReturn(false);
        Mockito.lenient().when(bancoContext.getClienteTemSaldo()).thenReturn(false);

        var retorno = unit.test(informacoesTransferencia, bancoContext);
        Assertions.assertEquals(valorEsperado, retorno);
    }
}
