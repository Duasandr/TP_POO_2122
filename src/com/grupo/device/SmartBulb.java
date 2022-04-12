package com.grupo.device;

import java.util.Objects;

public class SmartBulb extends SmartDevice{
    //Variáveis de instância
    private int tonalidade;
    private Double dimensao;

    //Construtores

    /**
     * Construtor vazio
     */
    protected SmartBulb(){
        this("null", false, 0, 0.0, 0, 0.0);
    }

    /**
     * Construtor por parâmetros
     *
     * @param id
     * @param estado
     * @param potencia
     * @param preco_instalacao
     * @param tone
     * @param dimensao
     */
    protected SmartBulb(String id , boolean estado , int potencia , double preco_instalacao , int tone , double dimensao){
        this.setIdFabricante(id);
        this.setEstado(estado);
        this.setPotencia(potencia);
        this.setPrecoInstalacao(preco_instalacao);
        this.setTonalidade(tone);
        this.setDimensao(dimensao);
    }

    protected SmartBulb(SmartBulb bulb){
        this(bulb.getIdFabricante(), bulb.getEstado(), bulb.getPotencia(), bulb.getPrecoInstalacao(), bulb.getTonalidade(), bulb.getDimensao());
    }

    //Métodos de instância

    public int getTonalidade(){
        return this.tonalidade;
    }

    public double getDimensao(){
        return this.dimensao;
    }

    public void setTonalidade(int tone){
        this.tonalidade = tone;
    }

    protected void setDimensao(double dimensao){
        this.dimensao = dimensao;
    }

    @Override
    public Double consumoEnergia() {
        if (this.getEstado()) {
            if (this.tonalidade >= 0 && this.tonalidade < 5) return (Double) (getPotencia() * 0.2);
            if (this.tonalidade > 5 && this.tonalidade < 10) return (Double) (getPotencia() * 0.4);
            if (this.tonalidade > 10 && this.tonalidade < 15) return (Double) (getPotencia() * 0.5);
        }
        return null;
    }

    @Override
    public SmartBulb clone() {
        return new SmartBulb(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o.getClass().getSimpleName().equals("SmartBulb"))) return false;
        SmartBulb bulb = (SmartBulb) o;
        return this.getIdFabricante().equals(bulb.getIdFabricante()) &&
                this.getEstado() == bulb.getEstado() &&
                this.getPotencia() == bulb.getPotencia() &&
                this.getTonalidade() == bulb.getTonalidade() &&
                this.getPrecoInstalacao().equals(bulb.getPrecoInstalacao()) &&
                this.getDimensao() == bulb.getDimensao();
    }

    @Override
    public String toString(){
        return null;
    }

    @Override
    public int hashCode(){return 0;}
}