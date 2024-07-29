package br.com.autbank.schedullers;

import arch.runtime.annotation.bridge.scheduling.TaskScheduler;
import br.com.autbank.model.EnviosExterior;
import br.com.autbank.repositories.BancoRepository;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Named
@Slf4j
@AllArgsConstructor
public class SchedullerStatusLiberadoEnvioExterior {

    private BancoRepository bancoRepository;

    @TaskScheduler(cron = "0 13 15 * * *")
    public void buscaStatusLiberado() {

        log.info("EXIBINDO EXEC AGENDADA");
        log.info("PROCURANDO POR STATUS LIBERADO");
        var envios = bancoRepository.findByStatusLiberado();
        for (EnviosExterior envio : envios) {
            bancoRepository.atualizaStatusEnvioExterior("ENVIADO", envio.getIdRemessa());
        }
        log.info("NUMERO DE ENVIOS_EXTERIOR COM STATUS LIBERADO = {}", envios.size());

    }
}
