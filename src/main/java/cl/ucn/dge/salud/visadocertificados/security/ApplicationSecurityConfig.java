package cl.ucn.dge.salud.visadocertificados.security;

import cl.ucn.dge.salud.visadocertificados.jwt.JwtConfig;
import cl.ucn.dge.salud.visadocertificados.jwt.JwtTokenVerfier;
import cl.ucn.dge.salud.visadocertificados.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import cl.ucn.dge.salud.visadocertificados.service.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * The secret key used on the JWT token.
     */
    private final SecretKey secretKey;

    /**
     * Configuration class with information about
     * the JWT token.
     */
    private final JwtConfig jwtConfig;

    /**
     * Necessary to give information about the user if
     * it's a manager.
     */
    private final ServicioUsuario servicioUsuario;


    /**
     * Main Constructor.
     * @param secretKey secret key used on the JWT token.
     * @param jwtConfig class with information about
     * @param servicioUsuario
     */
    @Autowired
    public ApplicationSecurityConfig(final SecretKey secretKey,
                                     final JwtConfig jwtConfig,
                                     final  ServicioUsuario servicioUsuario) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
        this.servicioUsuario = servicioUsuario;
    }

    /**
     * Set the security configuration for the HTTP
     * security.
     * @param http the HttpSecurity to be modified.
     * @throws Exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(
                        authenticationManager(),
                        jwtConfig,
                        secretKey,
                        servicioUsuario))
                .addFilterAfter(new JwtTokenVerfier(secretKey, jwtConfig),
                        JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/registro").permitAll()
                .antMatchers("/v1/documentos/**").permitAll()
                .antMatchers("/v1/reportes/solicitudes").permitAll()
                .anyRequest()
                .authenticated();
    }

    /**
     * Change the Cors configuration with the
     * one set on this method.
     * @return Class with the new Configuration for
     * Cors policy.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        source.registerCorsConfiguration(
                "/**", config.applyPermitDefaultValues());
        //allow Authorization to be exposed
        config.setExposedHeaders(Arrays.asList("Authorization"));
        return source;
    }

}
