package com.oasis.course.exception;

/**
 * Classe definida para ser a exception genérica,
 * onde todos os erros passarão por ela para ser tratados corremente.
 */

public class GenericException extends RuntimeException {

    private String codigo;

    public GenericException(String codigo) {
        super("Exceção genérica com código: " + codigo);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
