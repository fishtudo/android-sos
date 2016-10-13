package fishtudo.sos.configs;

import api.server.Config;

public class ConfigurationLoader {
    private String host = "mobile-aceite.tcu.gov.br";
    private int port = 80;
    private String protocol = "http";
    private String context = "mapa-da-saude";

    public void loadServerConfigurations() {
        Config.setConfigurations(host, port, protocol, context);
    }
}