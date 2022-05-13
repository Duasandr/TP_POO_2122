package com.grupo.exceptions;

public class DispositivoNaoExisteException extends Exception{
    public DispositivoNaoExisteException(String local){
        super(local + ": Dispositivo não existe.");
    }
}
