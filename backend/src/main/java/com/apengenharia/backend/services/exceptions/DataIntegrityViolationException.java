package com.apengenharia.backend.services.exceptions;

// CRIA A MENSAGEM DE EXCEÇÃO QUANDO UM VALOR DE CPF E/OU EMAIL JÁ EXISTIREM
public class DataIntegrityViolationException extends RuntimeException{
    // SERIALIZABLE CRIA UMA SEQUÊNCIA DE BYTES DAS INSTÂNCIAS DA CLASSE PARA QUE POSSAM
    // SEREM TRAFEGADAS EM REDE E ARMAZENADAS EM ARQUIVOS DE MEMÓRIA
    private static final long serialVersionUID = 1L;

    // CONSTRUTOR DA SUPER CLASSE
    public DataIntegrityViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataIntegrityViolationException(String message) {
        super(message);
    }
}
