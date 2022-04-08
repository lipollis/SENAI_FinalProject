package com.ApEng.Ap_Engenharia.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "parceiro")
public class Parceiro implements Serializable {

    private static final long serialVersionUID = 1L;

    // ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    private String nome;
    @NotEmpty
    private String celular;
    @NotEmpty
    private String endereco;
    @NotEmpty
    private String email;
    @NotEmpty
    @Column(unique = true)
    private String cnpj;

    private double valorParceiria;

    @OneToMany(mappedBy = "parceiro", cascade = CascadeType.REMOVE)
    private List<Projeto> projetos;

    // GETTER & SETTER
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Double getValorParceiria() {
        return valorParceiria;
    }

    public void setValorParceiria(Double valorParceiria) {
        this.valorParceiria = valorParceiria;
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }
}
