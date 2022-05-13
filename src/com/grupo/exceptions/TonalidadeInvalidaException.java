package com.grupo.exceptions;

public class TonalidadeInvalidaException  extends Exception{
    public TonalidadeInvalidaException(String local){
        super("Tonalidade inválida: " + local);
    }
}
