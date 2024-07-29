package br.com.autbank.repositories;

import arch.common.config.Config;
import br.com.autbank.model.Contas;
import br.com.autbank.model.EnviosExterior;
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

    private final String atualizaEnvioExteriorSQL;
    private final String atualizaSaldoSQL;
    private final String buscaStatusLiberadoEnviosExteriorSQL;


    public BancoRepository(JdbcTemplate jdbcTemplate, Config config) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertEnvioExteriorSQL = config.getValue("classpath:SQL/DML/INSERT_ENVIO_EXTERIOR.sql");
        this.selectContasSQL = config.getValue("classpath:SQL/DML/SELECT_CONTAS_BY_TITULAR_NROCONTA.sql");
        this.insertTransferenciasSQL = config.getValue("classpath:SQL/DML/INSERT_TRANSFERENCIAS.sql");
        this.atualizaEnvioExteriorSQL = config.getValue("classpath:SQL/DML/UPDATE_ENVIO_EXTERIOR.sql");
        this.atualizaSaldoSQL = config.getValue("classpath:SQL/DML/UPDATE_CONTA_SALDO.sql");
        this.buscaStatusLiberadoEnviosExteriorSQL = config.getValue("classpath:SQL/DML/SELECT_ENVIOS_EXTERIOR_LIBERADOS.sql");
    }

    public void registraEnvioExterior(Integer idRemessa, BigDecimal valor, String status) {
        jdbcTemplate.update(insertEnvioExteriorSQL, idRemessa, valor, status);
    }

    public void registraTransferencia(String nroContaCred, BigDecimal valor, Integer idRemessa) {
        jdbcTemplate.update(insertTransferenciasSQL, nroContaCred, valor, idRemessa);
    }

    public void atualizaStatusEnvioExterior(String status, Integer idRemessa) {
        jdbcTemplate.update(atualizaEnvioExteriorSQL, status, idRemessa);
    }

    public void atualizaSaldo(BigDecimal saldo, String titular, String nroConta) {
        jdbcTemplate.update(atualizaSaldoSQL, saldo, titular, nroConta);
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

    public List<EnviosExterior> findByStatusLiberado() {


        return jdbcTemplate.query(buscaStatusLiberadoEnviosExteriorSQL, new EnvioExteriorRowMapper());
    }

    private static class EnvioExteriorRowMapper implements RowMapper<EnviosExterior> {
        @Override
        public EnviosExterior mapRow(ResultSet rs, int rowNum) throws SQLException {
            EnviosExterior envio = new EnviosExterior();
            envio.setId(rs.getInt("ID"));
            envio.setIdRemessa(rs.getInt("ID_REMESSA"));
            envio.setValor(rs.getBigDecimal("VALOR"));
            envio.setStatusEnvio(rs.getString("STATUS_ENVIO"));
            envio.setDataStatus(rs.getDate("DATA_STATUS")); // Mapeie outros campos conforme necessário
            return envio;
        }
    }
}
