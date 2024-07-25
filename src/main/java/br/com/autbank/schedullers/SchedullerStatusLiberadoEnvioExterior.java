package br.com.autbank.schedullers;

import arch.runtime.annotation.bridge.scheduling.TaskScheduler;
import br.com.autbank.repositories.BancoRepository;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Named
@Slf4j
@AllArgsConstructor
public class SchedullerStatusLiberadoEnvioExterior {

    private BancoRepository bancoRepository;

    @TaskScheduler(cron = "0 29 11 * * *")
    public void buscaStatusLiberado() {

        log.info("EXIBINDO EXEC AGENDADA");
        log.info("PROCURANDO POR STATUS LIBERADO");
        var envios = bancoRepository.findByStatusLiberado();
        log.info("NUMERO DE ENVIOS_EXTERIOR COM STATUS LIBERADO = {}", envios.size());
    }
}
