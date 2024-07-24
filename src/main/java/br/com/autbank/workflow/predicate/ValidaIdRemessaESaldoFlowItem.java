package br.com.autbank.workflow.predicate;

import arch.common.http.body.HttpBodyHandlers;
import br.com.autbank.BancoConstants;
import br.com.autbank.workflow.contexts.BancoContext;
import core.autogen.models.InformacoesTransferencia;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.function.BiPredicate;
@Slf4j
@Named
public class ValidaIdRemessaESaldoFlowItem implements BiPredicate<InformacoesTransferencia, BancoContext> {

    @Override
    public boolean test(InformacoesTransferencia informacoesTransferencia, BancoContext bancoContext) {

        if(bancoContext.getClienteTemSaldo() && bancoContext.getIdRemessaExiste())
            return true;
        else
            return false;
    }
}
