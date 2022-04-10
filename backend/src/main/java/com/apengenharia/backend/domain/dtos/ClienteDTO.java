package com.apengenharia.backend.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.apengenharia.backend.domain.Cliente;
import com.apengenharia.backend.domain.enums.Perfil;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

// O DTO É USADO COMO BOA PRÁTICA PARA A TRANSFERÊNCIA DE DADOS.
// NÃO É ACONSELHAVEL TER OS ENDPOINTS, COMO DE BUSCA GET, USANDO
// O QUE ESTÁ GUARDADO NA ENTITY
public class ClienteDTO implements Serializable{
        // SERIALIZABLE CRIA UMA SEQUÊNCIA DE BYTES DAS INSTÂNCIAS DA CLASSE PARA QUE POSSAM
        // SEREM TRAFEGADAS EM REDE E ARMAZENADAS EM ARQUIVOS DE MEMÓRIA
        private static final long serialVersionUID = 1L;

        // ATRIBUTOS
        protected Integer id;
        @NotNull(message = "O campo NOME é obrigatório.")
        protected String nome;
        @NotNull(message = "O campo CPF é obrigatório.")
        @CPF
        protected String cpf;
        @NotNull(message = "O campo e-MAIL é obrigatório.")
        protected String email;
        @NotNull(message = "O campo SENHA é obrigatório.")
        protected String senha;
        protected Set<Integer> perfis= new HashSet<>(); // O SET PERMITE QUE NÃO HAJA VALORES IGUAIS NA LISTA

        @JsonFormat(pattern = "dd/MM/yyyy")
        protected LocalDate dataCriacao = LocalDate.now();

        // CONSTRUTOR DA SUPER CLASSE
        public ClienteDTO() {
            super();
            addPerfil(Perfil.CLIENTE);
        }

        public ClienteDTO(Cliente obj) {
            super();
            this.id = obj.getId();
            this.nome = obj.getNome();
            this.cpf = obj.getCpf();
            this.email = obj.getEmail();
            this.senha = obj.getSenha();
            this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
            this.dataCriacao = obj.getDataCriacao();
            addPerfil(Perfil.CLIENTE);
        }

        // GETTERS E SETTERS
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

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

        // RETORNA A DESCRIÇÃO DO PERFIL. DE OUTRA FORMA O QUE É EXIBIDO É APENAS O VALUE DO PERFIL
        public Set<Perfil> getPerfis() {
            return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
        }

        public void addPerfil(Perfil perfil) {
            this.perfis.add(perfil.getCodigo());
        }

        public LocalDate getDataCriacao() {
            return dataCriacao;
        }

        public void setDataCriacao(LocalDate dataCriacao) {
            this.dataCriacao = dataCriacao;
        }
    }