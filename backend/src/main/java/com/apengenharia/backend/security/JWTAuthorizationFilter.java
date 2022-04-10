package com.apengenharia.backend.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    // ATRIBUTOS
    private JWTUtil jwtUtil;
    private UserDetailsService userDetailsService;

    // CONSTRUTOR
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,
                                  UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    // MÉTODO
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // CAMPO QUE AUTORIZA A REQUISIÇÃO USANDO NO HEADER O TOKEN VALIDADO
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")) {
            // O TOKEN GERADO INICIA COM A PALAVRA 'BEARER ' QUE SÃO 7 CARACTERES, OU SEJA, AQUI ESTÁ AVISANDO QUE O TOKEN
            // SÓ INICIA DE FATO APÓS 7 CARACTERES
            UsernamePasswordAuthenticationToken authToken = getAuthentication(header.substring(7));
            if(authToken != null) {
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);
    }

    // VERIFICA SE O TOKEN É VÁLIDO
    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if(jwtUtil.tokenValido(token)) {
            String username = jwtUtil.getUsername(token);
            UserDetails details = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(details.getUsername(), null, details.getAuthorities());
        }
        return null;
    }
}
