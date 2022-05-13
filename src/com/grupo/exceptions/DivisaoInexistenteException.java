package com.grupo.exceptions;

public class DivisaoInexistenteException extends Exception{
    public DivisaoInexistenteException(){
        super("Divisão não existe.");
    }
}
