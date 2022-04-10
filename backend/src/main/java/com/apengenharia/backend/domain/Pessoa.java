package com.apengenharia.backend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.apengenharia.backend.domain.enums.Perfil;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity()
public abstract class Pessoa implements Serializable {
    // SERIALIZABLE CRIA UMA SEQUÊNCIA DE BYTES DAS INSTÂNCIAS DA CLASSE PARA QUE POSSAM
    // SEREM TRAFEGADAS EM REDE E ARMAZENADAS EM ARQUIVOS DE MEMÓRIA
    private static final long serialVersionUID = 1L;

    // ATRIBUTOS, NÃO PODE SER PRIVATE PQ OUTRAS CLASSES PRECISA ACESSAR ESSES ATRIBUTOS.
    // O PROTECTE É USADO PARA PROTEGER OS DADOS E PERMITIR QUE AS CLASSES FILHAS ACESSEM
    // CONCEITO DE HERANÇA
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    protected String nome;

    //@CPF
    @Column(unique = true)
    protected String cpf;

    @Column(unique = true)
    protected String email;
    protected String senha;

    //@ElementCollection(fetch = FetchType.EAGER) -> É UMA COLEÇÃO DE ELEMENTOS DO TIPO INTEGER,
    // QUE QDO DER GET, BUSCAR O USUÁRIO, A LISTA DE PERFIS VEM IMEDIATAMENTE
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    protected Set<Integer> perfis= new HashSet<>(); // O SET PERMITE QUE NÃO HAJA VALORES IGUAIS NA LISTA

    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate dataCriacao = LocalDate.now(); // MOMENTO ATUAL DA INSTÂNCIA CRIADA

    // CONSTRUTOR VAZIO
    public Pessoa() {
        super();
        addPerfil(Perfil.CLIENTE);
    }

    // CONSTRUTOR
    public Pessoa(Integer id, String nome, String cpf, String email, String senha) {
        super();
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        addPerfil(Perfil.CLIENTE);
    }

    // MÉTODOS ACESSORES E MODIFICADORES
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

    // HASH CODE EQUALS FAZ A COMPARAÇÃO DE UM DETERMINADO OBJETO
    // PELO SEU VALOR COM O VALOR DOS ATRIBUTOS, NÃO O VALOR EM MEMÓRIA
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pessoa other = (Pessoa) obj;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
