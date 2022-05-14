package com.grupo.exceptions;

public class NaoESpeakException extends Exception {
    public NaoESpeakException(String local){
        super(local + ": Não é uma Smart Speaker.");
    }
}
