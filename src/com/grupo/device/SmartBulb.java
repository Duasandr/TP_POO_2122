package com.grupo.device;

import exceptions.EstadoInvalidoException;
import exceptions.TonalidadeInvalidaException;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

/**
 * Class SmartBulb
 */
public class SmartBulb extends SmartDevice implements Serializable {
    private static final double valor_fixo = 0.1;

    /**
     * Possiveis estados da tonalidade
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
    public void setTonalidade(Tonalidade tone) {
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

    //Métodos herdados

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
                case FRIA -> consumo = valor_fixo + this.dimensao * 1.2;
                case NEUTRA -> consumo = valor_fixo + this.dimensao * 1;
                case QUENTE -> consumo = valor_fixo + this.dimensao * 1.4;
            }
        }
        return consumo;
    }

    //Métodos de parsing

    /**
     * Transforma uma String numa Tonalidade.
     * @param str String
     * @return Tonalidade
     * @throws TonalidadeInvalidaException Acontece quando não é reconhecida uma tonalidade.
     */
    public static Tonalidade parseTonalidade(String str) throws TonalidadeInvalidaException {
        return switch (str.toUpperCase(Locale.ROOT)) {
            case "FRIA" -> Tonalidade.FRIA;
            case "QUENTE" -> Tonalidade.QUENTE;
            case "NEUTRA" -> Tonalidade.NEUTRA;
            default -> throw new TonalidadeInvalidaException(str);
        };
    }

    /**
     * Transforma uma ‘string’ formatada numa SmartBulb.
     * @param tokens String
     * @return SmartBulb
     */
    public static SmartBulb parse(String[] tokens) throws TonalidadeInvalidaException, EstadoInvalidoException {
        return new SmartBulb(tokens[0],
                SmartDevice.parseEstado(tokens[1]),
                Double.parseDouble(tokens[2]),
                SmartBulb.parseTonalidade(tokens[3]),
                Double.parseDouble(tokens[2]));
    }

    //Métodos de herdados de Object

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
        if (!(o instanceof SmartBulb bulb)) return false;
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
        return "SmartBulb{" + "ID=" + this.getIdFabricante() +
                ", estado=" + this.getEstado().toString() +
                ", preco_instalacao=" + this.getPrecoInstalacao() +
                ", tonalidade=" + tonalidade +
                ", dimensao=" + dimensao +
                '}';
    }

    /**
     * Cria um indice por uma função de hash.
     *
     * @return Indice
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getIdFabricante(), this.getPrecoInstalacao());
    }
}