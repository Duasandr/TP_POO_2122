package com.grupo.exceptions;

public class DadosInsuficientesException extends Exception{
    public DadosInsuficientesException(String local){
        super(local + ": Dados insuficientes. Use o delorean para gerar alguma entropia.");
    }
}
