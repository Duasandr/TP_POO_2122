package com.grupo.exceptions;

public class NaoEBulbException extends Exception {
    public NaoEBulbException(String local){
        super(local + ": Não é uma Smart Bulb.");
    }
}
