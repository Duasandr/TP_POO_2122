package com.grupo.device;

public class SmartCamera extends SmartDevice {
    //Variáveis de instância
    //...

    //Construtores

    protected SmartCamera(){

    }

    protected SmartCamera(String id , boolean estado , int potencia , double preco_instalacao , int res , Double tamanho_ficheiro){

    }

    protected SmartCamera(SmartCamera cam){

    }

    //Métodos de instância

    public int getResolucao(){
        return 0;
    }

    public Double getTamanhoFicheiro(){
        return 0.0;
    }

    public void setResolucao(int res){

    }

    public void setTamanhoFicheiro(Double tamanho_ficheiro){

    }

    @Override
    public Double consumoEnergia() {
        return null;
    }

    @Override
    public SmartCamera clone() {
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
