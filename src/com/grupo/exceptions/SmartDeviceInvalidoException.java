package com.grupo.exceptions;

public class SmartDeviceInvalidoException  extends Exception{
    public SmartDeviceInvalidoException(String local){
        super("SmartDevice inválido: " + local);
    }
}
