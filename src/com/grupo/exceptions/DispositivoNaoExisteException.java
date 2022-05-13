package com.grupo.exceptions;

public class DispositivoNaoExisteException extends Exception{
    public DispositivoNaoExisteException(){
        super("Dispositivo não existe.");
    }
}
