package com.lemoreiradev.listadetarefa.domain.exceptions;

public class NegocioExeption extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public NegocioExeption(String msg) {
        super(msg);
    }
}
