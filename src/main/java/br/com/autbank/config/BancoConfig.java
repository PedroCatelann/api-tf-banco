package br.com.autbank.config;

import arch.common.config.Config;
import br.com.autbank.BancoConstants;
import jakarta.inject.Named;
import lombok.Getter;

import java.net.URI;
import java.net.URISyntaxException;

@Named
@Getter
public class BancoConfig {

    private final URI uriRemessa;

    public BancoConfig(Config config) throws URISyntaxException {
        this.uriRemessa = config.getOptionalValue(BancoConstants.URI_REMESSA, URI.class).orElse(new URI(BancoConstants.DEFAULT_URI_REMESSA));
    }
}
