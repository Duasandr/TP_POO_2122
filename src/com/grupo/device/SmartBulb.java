package com.grupo.device;

import java.util.Objects;

/**
 * Class SmartBulb
 */
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
     * @param id Identificador do fabricante.
     * @param estado Estado atual do aparelho (ligado - desligado).
     * @param potencia Potência de consumo do aparelho.
     * @param preco_instalacao Preço de instalação definido pelo fabricante.
     * @param tone Tipo de luz selecionada.
     * @param dimensao Tamanho do aparelho.
     */
    protected SmartBulb(String id , boolean estado , int potencia , Double preco_instalacao , int tone , Double dimensao){
        this.setIdFabricante(id);
        this.setEstado(estado);
        this.setPotencia(potencia);
        this.setPrecoInstalacao(preco_instalacao);
        this.setTonalidade(tone);
        this.setDimensao(dimensao);
    }

    /**
     * Construtor por cópia.
     * @param bulb Aparelho a ser copiado.
     */
    protected SmartBulb(SmartBulb bulb){
        this(bulb.getIdFabricante(), bulb.getEstado(), bulb.getPotencia(), bulb.getPrecoInstalacao(), bulb.getTonalidade(), bulb.getDimensao());
    }

    //Métodos de instância

    //Getters

    /**
     * Devolve a intensidade da luz do aparelho.
     * @return Tipo de luz.
     */
    public int getTonalidade(){
        return this.tonalidade;
    }

    /**
     * Devolve as dimensões do aparelho.
     * @return Dimensões do aparelho.
     */
    public double getDimensao(){
        return this.dimensao;
    }

    /**
     * Define a intensidade da luz no aparelho.
     * @param tone Intensidade da luz.
     */
    public void setTonalidade(int tone){
        this.tonalidade = tone;
    }

    /**
     * Define as dimensões do aparelho.
     * @param dimensao Dimensões do aparelho.
     */
    protected void setDimensao(Double dimensao){
        this.dimensao = dimensao;
    }

    /**
     * Devolve o consumo de energia do aparelho.
     * @return Energia consumida pelo aparelho.
     */
    @Override
    public Double consumoEnergia() {
        if (this.getEstado()) {
            if (this.tonalidade >= 0 && this.tonalidade < 5) return getPotencia() * 0.2;
            if (this.tonalidade > 5 && this.tonalidade <= 10) return getPotencia() * 0.4;
            if (this.tonalidade > 10 && this.tonalidade < 15) return getPotencia() * 0.5;
        }
        return null;
    }

    /**
     * Clona o aparelho.
     * @return Aparelho clonado.
     */
    @Override
    public SmartBulb clone() {
        return new SmartBulb(this);
    }

    /**
     * Verifica se dois objetos são iguais.
     * @param o Objeto a comparar.
     * @return Verdadeiro ou Falso.
     */
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

    /**
     * Devolve uma representação textual do objeto.
     * @return Representação do objeto.
     */
    @Override
    public String toString(){
        return super.toString() + "{ Tonalidade: " + this.getTonalidade() +
                ", Dimensão: " + this.getDimensao() +
                "}";
    }

    /**
     * Cria um indice através de uma função de hash.
     * @return Indice
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getIdFabricante(), this.getPotencia(), this.getPrecoInstalacao());
    }
}