package com.apengenharia.backend.domain.dtos;

public class CredenciaisDTO {
    // CLASSE PARA A CONVERSÃO DO USUÁRIO COM A REQUISIÇÃO DE LOGIN

    // ATRIBUTOS
    private String email;
    private String senha;

    // GETTERS E SETTERS
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
