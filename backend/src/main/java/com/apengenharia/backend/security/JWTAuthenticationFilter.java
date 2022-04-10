package com.apengenharia.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.apengenharia.backend.domain.dtos.CredenciaisDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    // INTERCEPTA O MÉTODO POST

    // É A PRINCIPAL INTERFACE DE ESTRATÉGIA PARA AUTENTICAÇÃO. SE A AUTENTIFICAÇÃO DE ENTRADA
    // FOR VÁLIDA, É VERIFICADO O MÉTODO QUE ELE POSSUI COMO AUTHENTICATION RETORNA UMA INSTÂNCIA
    // DE AUTENTICAÇÃO COM UM SINALIZADOR DEFINIDO COMO VERDADEIRO.
    // SE NÃO FOR VÁLIDO, ESSE SINALIZADOR VAI RETORNAR UM VALOR NULO
    private AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;

    // CONSTRUTOR
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        super();
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    // MÉTODO PARA AUTENTIFICAÇÃO
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            // VALORES CONVERTIDOS PARA O DTO
            CredenciaisDTO creds = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());
            return authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    // MÉTODO PARA AUTENTIFICAÇÃO COM SUCESSO
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String username = ((UserSS) authResult.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(username);
        // NECESSÁRIO PARA COLETAR O TOKEN DEPOIS
        response.setHeader("access-control-expose-headers", "Authorization");
        response.setHeader("Authorization", "Bearer " + token);
    }

    @Override
    // MÉTODO PARA AUTENTIFICAÇÃO NÃO SUCEDIDA
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        response.setStatus(401);
        response.setContentType("application/json");
        response.getWriter().append(json());
    }

    // CONTEÚDO DA MENSAGEM DE ERRO PARA LOGIN INCORRETO
    private CharSequence json() {
        long date = new Date().getTime();
        return "{"
                + "\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\", "
                + "\"path\": \"/login\"}";
    }
}
