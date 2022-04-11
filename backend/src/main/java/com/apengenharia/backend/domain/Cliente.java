package com.apengenharia.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.apengenharia.backend.domain.dtos.ClienteDTO;
import com.apengenharia.backend.domain.enums.Perfil;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Cliente extends Pessoa {
    // SERIALIZABLE CRIA UMA SEQUÊNCIA DE BYTES DAS INSTÂNCIAS DA CLASSE PARA QUE POSSAM
    // SEREM TRAFEGADAS EM REDE E ARMAZENADAS EM ARQUIVOS DE MEMÓRIA
    // POR CAUSA DO EXTENDS PESSOA QUE POSSUI SERIALIZAÇÃO
    private static final long serialVersionUID = 1L;

    // ATRIBUTOS
    @JsonIgnore // IMPEDE A SERIALIZAÇÃO DAS INFORMAÇÕES QUANDO FIZER REQUISIÇÃO GET
    @OneToMany(mappedBy = "cliente") // UM CLIENTE PARA MUITOS CHAMADOS - mapeado com o nome cliente em Projeto
    private List<Projeto> projetos = new ArrayList<>();

    // CONSTRUTOR DA SUPER CLASSE
    public Cliente() {
        super();
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(ClienteDTO obj) {
        super();
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.senha = obj.getSenha();
        this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
        this.dataCriacao = obj.getDataCriacao();
    }

    // GETTER E SETTER
    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }
}