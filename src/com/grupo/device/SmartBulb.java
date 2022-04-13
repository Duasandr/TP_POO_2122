package com.grupo.device;

import java.util.Locale;
import java.util.Objects;

/**
 * Class SmartBulb
 */
public class SmartBulb extends SmartDevice implements Bulb{
    /**
     * Posiveis estados da tonalidade
     */
    private enum Tonalidade {FRIA , NEUTRA , QUENTE}

    //Variáveis de instância
    private Tonalidade tonalidade;
    private double dimensao;

    //Construtores

    /**
     * Construtor vazio
     */
    public SmartBulb(){
        this("null", "desligado", 0, 0.0, "fria", 0.0);
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
    public SmartBulb(String id , String estado , int potencia , double preco_instalacao , String tone , double dimensao){
        super(id,estado,potencia,preco_instalacao);
        this.setTonalidade(tone);
        this.setDimensao(dimensao);
    }

    /**
     * Construtor por cópia.
     * @param bulb Aparelho a ser copiado.
     */
    public SmartBulb(SmartBulb bulb){
        this(bulb.getIdFabricante(), bulb.getEstado(), bulb.getPotencia(), bulb.getPrecoInstalacao(), bulb.getTonalidade(), bulb.getDimensao());
    }

    //Métodos de instância

    //Getters

    /**
     * Devolve a intensidade da luz do aparelho.
     * @return Tipo de luz.
     */
    public String getTonalidade(){
        return this.tonalidade.toString();
    }

    /**
     * Devolve as dimensões do aparelho.
     * @return Dimensões do aparelho.
     */
    public double getDimensao(){
        return this.dimensao;
    }

    //Setters

    /**
     * Define a intensidade da luz no aparelho.
     * @param tone Intensidade da luz.
     */
    private void setTonalidade(String tone){
        if(tone != null) {
            String nova_tonalidade = tone.toUpperCase(Locale.ROOT);
            if (nova_tonalidade.equals(Tonalidade.FRIA.toString()) ||
                    nova_tonalidade.equals(Tonalidade.NEUTRA.toString()) ||
                    nova_tonalidade.equals(Tonalidade.QUENTE.toString())) {
                this.tonalidade = Tonalidade.valueOf(nova_tonalidade);
            }
        }
    }

    /**
     * Define as dimensões do aparelho.
     * @param dimensao Dimensões do aparelho.
     */
    private void setDimensao(double dimensao){
        this.dimensao = dimensao;
    }

    //Métodos de interface

    /**
     * Devolve o consumo de energia do aparelho.
     * @return Energia consumida pelo aparelho.
     */
    @Override
    public double getConsumoEnergia() {
        double consumo = 0.0;
        if(this.getEstado().toUpperCase(Locale.ROOT).equals("LIGADO")){
            consumo = this.getPotencia() + this.tonalidade.ordinal();
        }
        return consumo;
    }

    /**
     * Muda a tonalidade da lâmpada.
     * @param tone Tonalidade selecionada (frio , neutro , quente)
     */
    @Override
    public void mudaTonalidade(String tone) {
        this.setTonalidade(tone);
    }

    //Métodos de Object

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
                Objects.equals(this.getEstado(), bulb.getEstado()) &&
                this.getPotencia() == bulb.getPotencia() &&
                Objects.equals(this.getTonalidade(), bulb.getTonalidade()) &&
                Double.compare(this.getPrecoInstalacao(),bulb.getPrecoInstalacao()) == 0 &&
                Double.compare(this.getDimensao(),bulb.getDimensao()) == 0;
    }

    /**
     * Devolve uma representação textual do objeto.
     * @return Representação do objeto.
     */
    @Override
    public String toString(){
        return super.toString() + "{ Tonalidade: " + this.getTonalidade() +
                ", Dimensao: " + this.getDimensao() +
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