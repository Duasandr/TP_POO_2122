package com.grupo.device;

import java.util.Objects;

public class SmartBulb extends SmartDevice{
    //Variáveis de instância
    //...

    //Construtores

    protected SmartBulb(){

    }

    protected SmartBulb(String id , boolean estado , int potencia , double preco_instalacao , int tone , double dimensao){

    }

    protected SmartBulb(SmartBulb bulb){

    }

    //Métodos de instância

    public int getTonalidade(){
        return 0;
    }

    public double getDimensao(){
        return 0.0;
    }

    public void setTonalidade(int tone){

    }

    protected void setDimensao(double dimensao){

    }

    @Override
    public Double consumoEnergia() {
        return null;
    }

    @Override
    public SmartBulb clone() {
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

    @Override
    public int hashCode(){return 0;}
}
