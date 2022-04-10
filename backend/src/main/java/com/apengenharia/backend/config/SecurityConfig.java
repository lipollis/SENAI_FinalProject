package com.apengenharia.backend.config;

import com.apengenharia.backend.security.JWTAuthenticationFilter;
import com.apengenharia.backend.security.JWTAuthorizationFilter;
import com.apengenharia.backend.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // PARA LIBERAR O ACESSO AO SQL PELO h2-console
    private static final String[] PUBLIC_MATCHERS = { "/h2-console/**" };

    @Autowired
    //INTERFACE QUE REPRESENTA O AMBIENTE NO QUAL O APP ATUAL ESTÁ SENDO EXECUTADO. HABILITA A INTERFACE
    // DO H2 QUANDO SE ESTÁ EM AMBIENTE DE "test"
    private Environment env;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    // MÉTODO PARA QUE QUALQUER ENDPOINT QUE NECESSITE DE DEFESA CONTRA VULNERABILIDADE É ESPECIFICADO AQUI
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // VALIDA SE O AMBIENTE DE DESENVOLVIMENTO É O DE test, QUE ENTÃO CHAMA O HATTP HEADERS E DESABILITA O frameOptions
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        // DESABILITA O ATAQUE DO TIPO CSRF QUE CONSISTE EM ATAQUES DE SESSÃO ARMAZENADA DO USUÁRIO, MAS COMO ESTA
        // APLICAÇÃO NÃO ARMAZENA SESSÃO, NÃO PRECISA SER MANTIDA, EM CONTRAPARTIDA É PRECISO GARANTIR QUE A
        // SESSÃO NÃO SERÁ CRIADA, ENTÃO É EMPREGADO sessionManagement
        http.cors().and().csrf().disable();
        // FILTROS DE AUTENTICAÇÃO E AUTORIZAÇÃO
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
        // para a autorização do h2-console
        http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    // A SENHA ESTAVA SALVA NO BANCO O QUE PERMITE QUE QUALQUER UM QUE TENHA ACESSO A ESTE BANCO TAMBÉM TEM PODER
    // DESTAS SENHAS, O QUE É UMA FALHA DE SEGURANÇA,ESTE BEAN ENTÃO FORNECE A SENHA COMO CRIPTOGRAFADA NO BANCO
    // É INJETADO EM QUALQUER PARTE DO PROJETO
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
