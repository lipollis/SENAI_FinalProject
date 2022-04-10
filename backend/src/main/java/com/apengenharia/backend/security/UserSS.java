package com.apengenharia.backend.security;

import com.apengenharia.backend.domain.enums.Perfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

// USER SPRING SECURITY
public class UserSS implements UserDetails {
    // SERIAL É IMPLEMENTADO NO UserDetails, ENTÃO PRECISA SER INSTÂNCIA AQUI
    private static final long serialVersionUID = 1L;

    // ATRIBUTOS
    private Integer id;
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;

    // CONSTRUTOR COM OS PARÂMETROS
    public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
        super();
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toSet());
    }

    // GETTER
    public Integer getId() {
        return id;
    }

    // MÉTODOS NÃO IMPLEMENTADOS DE USER DETAILS, REGRAS DE NEGÓCIO PADRÃO COMO CONTA EXPIRADA, CONTA BLOQUEADA, ETC
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

