package com.apengenharia.backend.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
    private static final long serialVersionUID = 1L;

    // JÁ POSSUI TODOS OS ATRIBUTOS DE StandardError (extends = herança)
    // E TEM UM ATRIBUTO A MAIS COM A LISTA DE MENSAGEM
    private List<FieldMessage> errors = new ArrayList<>();

    // CONSTRUTOR DA SUPER CLASSE
    public ValidationError() {
        super();
    }

    // CONSTRUTOR COM OS PARÂMETROS
    public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    // GETTERS E SETTERS
    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String message) {
        this.errors.add(new FieldMessage(fieldName, message));
    }
}
