package br.com.zup.renatomelo.proposta.proposta.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                authorizeRequests
                        .antMatchers(HttpMethod.POST, "/api/v1/dados/**").hasAuthority("SCOPE_proposta:write")
                        .antMatchers(HttpMethod.POST, "/api/v1/biometrias/**").hasAuthority("SCOPE_biometrias:write")
                        .antMatchers(HttpMethod.POST, "/api/v1/cartoes/**/bloquear").hasAuthority("SCOPE_cartao:write")
                        .antMatchers(HttpMethod.GET, "/api/v1/dados/**").hasAuthority("SCOPE_proposta:read")
                        .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                        .anyRequest().authenticated()
        )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}
