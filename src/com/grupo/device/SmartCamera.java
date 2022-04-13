package com.grupo.device;

public class SmartCamera extends SmartDevice {
    //Variáveis de instância
    //...

    //Construtores

    protected SmartCamera(){

    }

    protected SmartCamera(String id , String estado , int potencia , double preco_instalacao , int res , double tamanho_ficheiro){

    }

    protected SmartCamera(SmartCamera cam){

    }

    //Métodos de instância

    public int getResolucao(){
        return 0;
    }

    public double getTamanhoFicheiro(){
        return 0.0;
    }

    public void setResolucao(int res){

    }

    public void setTamanhoFicheiro(Double tamanho_ficheiro){

    }

    @Override
    public double getConsumoEnergia() {
        return 0.0;
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
