package cl.ucn.dge.salud.visadocertificados.service.email;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class EmailConfig {

    private String username;

    private String pathLogo;

    public EmailConfig() {
    }

    public String getUsername() {
        return username;
    }

    public String getPathLogo() {
        return pathLogo;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPathLogo(String pathLogo) {
        this.pathLogo = pathLogo;
    }
}
