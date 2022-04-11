package com.grupo.device;

import java.util.Objects;

public class SmartSpeaker extends SmartDevice{
    //Variáveis de instância
    //...

    //Construtores

    protected SmartSpeaker(){

    }

    protected SmartSpeaker(String id , boolean estado , int potencia , double preco_instalacao , int vol , String canal , int max_vol){

    }

    protected SmartSpeaker(SmartSpeaker speaker){

    }

    //Métodos de instância

    public int getVolume(){
        return 0;
    }

    public String getCanal(){
        return null;
    }

    public int getVolumeMaximo(){
        return 0;
    }

    public void setVolume(int vol){

    }

    public void setCanal(String canal){

    }

    protected void setVolumeMaximo(int max){

    }

    @Override
    public Double consumoEnergia() {
        return null;
    }

    @Override
    public SmartSpeaker clone() {
        return null;
    }

    @Override
    public boolean equals(Object o){
        return false;
    }

    @Override
    public String toString(){
        return null;
    }
}
