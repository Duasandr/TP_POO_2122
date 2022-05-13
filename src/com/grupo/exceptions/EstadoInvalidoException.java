package com.grupo.exceptions;

public class EstadoInvalidoException extends Exception {
    public EstadoInvalidoException(String local){ super("Estado inválido: " + local);}
}
