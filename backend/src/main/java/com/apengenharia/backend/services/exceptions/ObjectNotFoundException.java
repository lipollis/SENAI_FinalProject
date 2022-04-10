package com.apengenharia.backend.services.exceptions;

// CRIA A MENSAGEM DE EXCEÇÃO QUANDO UM VALOR DE ID, POR EXEMPLO, NÃO É ENCONTRADO
public class ObjectNotFoundException extends RuntimeException{
    // SERIALIZABLE CRIA UMA SEQUÊNCIA DE BYTES DAS INSTÂNCIAS DA CLASSE PARA QUE POSSAM
    // SEREM TRAFEGADAS EM REDE E ARMAZENADAS EM ARQUIVOS DE MEMÓRIA
    private static final long serialVersionUID = 1L;

    // CONSTRUTOR DA SUPER CLASSE
    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
