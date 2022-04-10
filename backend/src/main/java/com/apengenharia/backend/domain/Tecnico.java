package com.apengenharia.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.apengenharia.backend.domain.dtos.TecnicoDTO;
import com.apengenharia.backend.domain.enums.Perfil;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Tecnico extends Pessoa{
    // SERIALIZABLE CRIA UMA SEQUÊNCIA DE BYTES DAS INSTÂNCIAS DA CLASSE PARA QUE POSSAM
    // SEREM TRAFEGADAS EM REDE E ARMAZENADAS EM ARQUIVOS DE MEMÓRIA
    // POR CAUSA DO EXTENDS PESSOA QUE POSSUI SERIALIZAÇÃO
    private static final long serialVersionUID = 1L;

    // ATRIBUTOS
    @JsonIgnore // IMPEDE A SERIALIZAÇÃO DAS INFORMAÇÕES QUANDO FIZER REQUISIÇÃO GET
    @OneToMany(mappedBy = "tecnico") // UM TÉCNICO PARA MUITOS CHAMADOS - mapeado com o nome tecnico em Projeto
    private List<Projeto> chamados = new ArrayList<>();

    // CONSTRUTOR DA SUPER CLASSE
    public Tecnico() {
        super();
        addPerfil(Perfil.TECNICO);
    }

    public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        addPerfil(Perfil.TECNICO);
    }

    public Tecnico(TecnicoDTO obj) {
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
    public List<Projeto> getChamados() {
        return chamados;
    }

    public void setChamados(List<Projeto> chamados) {
        this.chamados = chamados;
    }
}