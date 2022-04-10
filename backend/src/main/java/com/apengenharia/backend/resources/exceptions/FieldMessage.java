package com.apengenharia.backend.resources.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    // CAMPOS QUE PRECISAM DE TRATAMENTO
    private String fieldName;
    private String message;

    // CONSTRUTOR DA SUPER CLASSE
    public FieldMessage() {
        super();
    }

    // CONSTRUTOR COM OS PARÂMETROS
    public FieldMessage(String fieldName, String message) {
        super();
        this.fieldName = fieldName;
        this.message = message;
    }

    // GETTERS E SETTERS
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
