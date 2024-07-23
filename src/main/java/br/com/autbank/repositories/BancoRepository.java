package br.com.autbank.repositories;

import arch.common.config.Config;
import jakarta.inject.Named;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

@Named
public class BancoRepository {
    private final JdbcTemplate jdbcTemplate;
    private final String insertEnvioExteriorSQL;

    public BancoRepository(JdbcTemplate jdbcTemplate, Config config) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertEnvioExteriorSQL = config.getValue("classpath:SQL/DML/INSERT_ENVIO_EXTERIOR.sql");
    }

    public void registraEnvioExterior(Integer idRemessa, BigDecimal valor, String status) {
        jdbcTemplate.update(insertEnvioExteriorSQL, idRemessa, valor, status);
    }
}
