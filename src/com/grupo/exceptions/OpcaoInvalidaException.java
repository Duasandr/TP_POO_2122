package com.grupo.exceptions;

public class OpcaoInvalidaException extends Exception {
    public OpcaoInvalidaException(String local){super(local + ": Opção inválida.");}
}
