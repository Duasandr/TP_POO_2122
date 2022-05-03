package com.grupo.device;

import java.util.Objects;

/**
 * Class SmartBulb
 */
public class SmartBulb extends SmartDevice {
    private static final double valor_fixo = 0.1;

    /**
     * Posiveis estados da tonalidade
     */
    public enum Tonalidade {
        FRIA,
        NEUTRA,
        QUENTE
    }

    //Variáveis de instância
    private Tonalidade tonalidade;
    private double dimensao;

    //Construtores

    /**
     * Construtor vazio
     */
    public SmartBulb() {
    }

    /**
     * Construtor por parâmetros
     *
     * @param id               Identificador do fabricante.
     * @param estado           Estado atual do aparelho (ligado - desligado).
     * @param preco_instalacao Preço de instalação definido pelo fabricante.
     * @param tone             Tipo de luz selecionada.
     * @param dimensao         Tamanho do aparelho.
     */
    public SmartBulb(String id, Estado estado, double preco_instalacao, Tonalidade tone, double dimensao) {
        super(id, estado, preco_instalacao);
        this.tonalidade = tone;
        this.dimensao = dimensao;
    }

    /**
     * Construtor por cópia.
     *
     * @param bulb Aparelho a ser copiado.
     */
    public SmartBulb(SmartBulb bulb) {
        this(bulb.getIdFabricante(), bulb.getEstado(), bulb.getPrecoInstalacao(), bulb.tonalidade, bulb.dimensao);
    }

    //Métodos de instância

    //Getters

    /**
     * Devolve a intensidade da luz do aparelho.
     *
     * @return Tipo de luz.
     */
    public Tonalidade getTonalidade() {
        return this.tonalidade;
    }

    /**
     * Devolve as dimensões do aparelho.
     *
     * @return Dimensões do aparelho.
     */
    public double getDimensao() {
        return this.dimensao;
    }

    //Setters

    /**
     * Define a intensidade da luz no aparelho.
     *
     * @param tone Intensidade da luz.
     */
    private void setTonalidade(Tonalidade tone) {
        if (tone != null) {
            this.tonalidade = tone;
        }
    }

    /**
     * Define as dimensões do aparelho.
     *
     * @param dimensao Dimensões do aparelho.
     */
    public void setDimensao(double dimensao) {
        this.dimensao = dimensao;
    }

    //Métodos de interface

    /**
     * Devolve o consumo de energia do aparelho.
     *
     * @return Energia consumida pelo aparelho.
     */
    @Override
    public double getConsumoEnergia() {
        double consumo = 0.0;
        if (this.getEstado() == Estado.LIGADO) {
            switch (this.tonalidade) {
                case FRIA -> consumo = valor_fixo + this.getDimensao() * 1.2;
                case NEUTRA -> consumo = valor_fixo + this.getDimensao() * 1;
                case QUENTE -> consumo = valor_fixo + this.getDimensao() * 1.4;
            }
        }
        return consumo;
    }

    /**
     * Muda a tonalidade da lâmpada.
     *
     * @param tone Tonalidade selecionada (frio , neutro , quente)
     */
    public void mudaTonalidade(Tonalidade tone) {
        this.setTonalidade(tone);
    }

    //Métodos de Object

    /**
     * Clona o aparelho.
     *
     * @return Aparelho clonado.
     */
    @Override
    public SmartBulb clone() {
        return new SmartBulb(this);
    }

    /**
     * Verifica se dois objetos são iguais.
     *
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
                Objects.equals(this.getTonalidade(), bulb.getTonalidade()) &&
                Double.compare(this.getPrecoInstalacao(), bulb.getPrecoInstalacao()) == 0 &&
                Double.compare(this.getDimensao(), bulb.getDimensao()) == 0;
    }

    /**
     * Devolve uma representação textual do objeto.
     *
     * @return Representação do objeto.
     */
    @Override
    public String toString() {
        return super.toString() + "{ Tonalidade: " + this.getTonalidade() +
                ", Dimensao: " + this.getDimensao() +
                "}";
    }

    /**
     * Cria um indice através de uma função de hash.
     *
     * @return Indice
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getIdFabricante(), this.getPrecoInstalacao());
    }
}