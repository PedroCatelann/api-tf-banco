package br.com.autbank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnviosExterior {
    private Integer id;
    private Integer idRemessa;
    private BigDecimal valor;
    private String statusEnvio;
    private Date dataStatus;
}
