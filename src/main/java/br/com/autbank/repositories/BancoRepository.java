package br.com.autbank.repositories;

import arch.common.config.Config;
import br.com.autbank.model.Contas;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Named
@Slf4j
public class BancoRepository {
    private final JdbcTemplate jdbcTemplate;
    private final String insertEnvioExteriorSQL;
    private final String selectContasSQL;

    private final String insertTransferenciasSQL;

    public BancoRepository(JdbcTemplate jdbcTemplate, Config config) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertEnvioExteriorSQL = config.getValue("classpath:SQL/DML/INSERT_ENVIO_EXTERIOR.sql");
        this.selectContasSQL = config.getValue("classpath:SQL/DML/SELECT_CONTAS_BY_TITULAR_NROCONTA.sql");
        this.insertTransferenciasSQL = config.getValue("classpath:SQL/DML/INSERT_TRANSFERENCIAS.sql");
    }

    public void registraEnvioExterior(Integer idRemessa, BigDecimal valor, String status) {
        jdbcTemplate.update(insertEnvioExteriorSQL, idRemessa, valor, status);
    }

    public void registraTransferencia(String nroContaCred, BigDecimal valor, Integer idRemessa) {
        jdbcTemplate.update(insertTransferenciasSQL, nroContaCred, valor, idRemessa);
    }

    public Contas buscaContas(String titular, String nroConta) {

        try {
            return jdbcTemplate.queryForObject(selectContasSQL, new Object[]{nroConta, titular}, new RowMapper<Contas>() {
                @Override
                public Contas mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Contas contas = new Contas();
                    contas.setSaldo(rs.getBigDecimal("SALDO"));

                    return contas;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            log.error("ERRO AO BUSCAR A CONTA DO TITULAR");
            log.error(e.getMessage());
            return null;
        }
    }
}
