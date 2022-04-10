package com.apengenharia.backend.domain.enums;

public enum Prioridade {
    BAIXA(0, "BAIXA"), MEDIA(1, "MEDIA"), ALTA(2, "ALTA");

    // ATRIBUTOS
    private Integer codigo;
    private String descricao;

    // CONSTRUTOR
    Prioridade(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    // GETTERS
    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    // MÉTODO STATIC PARA NÃO PRECISAR CRIAR UMA INSTÂNCIA
    // DE PERFIL PARA CHAMAR ESTE MÉTODO EM OUTRAS PARTES DO CÓDIGO
    public static Prioridade toEnum(Integer cod){
        if(cod == null){
            return null;
        }
        for(Prioridade x : Prioridade.values()){
            if(cod.equals(x.getCodigo())){
                return x;
            }
        }
        throw new IllegalArgumentException("Prioridade inválida");
    }
}
