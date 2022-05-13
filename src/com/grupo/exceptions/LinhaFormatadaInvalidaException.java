package com.grupo.exceptions;

public class LinhaFormatadaInvalidaException extends Exception{
    public LinhaFormatadaInvalidaException(String linha){
        super("Linha formatada indevidamente: " + linha);
    }
}
