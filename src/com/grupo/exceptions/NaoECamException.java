package com.grupo.exceptions;

public class NaoECamException extends Exception {
    public NaoECamException(String local){
        super(local + ": Não é uma Smart Cam.");
    }
}
