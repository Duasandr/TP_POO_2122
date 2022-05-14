package com.grupo.exceptions;

public class DivisaoNaoExisteException extends Exception{
    public DivisaoNaoExisteException(String local){
        super(local + ": Divisão não existe.");
    }
}
