package com.grupo.exceptions;

public class CasaInexistenteException extends Exception{
    public CasaInexistenteException(){
        super("Casa não existe.");
    }
}
