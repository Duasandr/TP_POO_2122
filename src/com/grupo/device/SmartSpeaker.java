package com.grupo.device;

import com.grupo.exceptions.EstadoInvalidoException;

import java.io.Serializable;
import java.util.Objects;


/**
 * Class SmartSpeaker
 */
public class SmartSpeaker extends SmartDevice implements Serializable {
    // Variáveis de instância
    private String canal;
    private int volume_max;
    private int volume;
    private static final double valor_fixo = 0.4;

    //Construtores

    /**
     * Construtor Vazio
     */
    public SmartSpeaker() {
    }

    /**
     * Construtor por parâmetros
     *
     * @param id               Identificador do fabricante.
     * @param estado           Estado atual do aparelho (ligado - desligado).
     * @param preco_instalacao Preço de instalação definido pelo fabricante.
     * @param volume           Estado atual do volume do aparelho.
     * @param canal            Canal atual do aparelho.
     * @param volume_max       Volume máximo do aparelho.
     */
    public SmartSpeaker(String id, Estado estado, double preco_instalacao, int volume, String canal, int volume_max) {
        super(id, estado, preco_instalacao);
        this.volume = volume;
        this.canal = canal;
        this.volume_max = volume_max;
    }

    /**
     * Construtor por cópia
     *
     * @param speaker Aparelho a ser copiado.
     */
    public SmartSpeaker(SmartSpeaker speaker) {
        this(speaker.getIdFabricante(), speaker.getEstado(), speaker.getPrecoInstalacao(), speaker.volume, speaker.canal, speaker.volume_max);
    }

    //Métodos de instância

    //Getters

    /**
     * Devolve o volume atual do aparelho selecionado.
     *
     * @return Volume do aparelho.
     */
    public int getVolume() {
        return this.volume;
    }

    /**
     * Devolve o canal que está a tocar no aparelho.
     *
     * @return Canal.
     */
    public String getCanal() {
        return this.canal;
    }

    /**
     * Devolve o volume máximo do aparelho selecionado.
     *
     * @return Volume máximo.
     */
    public int getVolumeMaximo() {
        return this.volume_max;
    }

    //Setters

    /**
     * Define o volume do aparelho selecionado.
     *
     * @param volume Volume do aparelho.
     */
    public void setVolume(int volume) {
        this.volume = volume >= 0 && volume <= this.volume_max ? volume : this.volume;
    }

    /**
     * Define o canal a que está ligado o aparelho.
     *
     * @param canal Canal.
     */
    public void setCanal(String canal) {
        this.canal = canal;
    }

    /**
     * Define o volume máximo do aparelho selecionado.
     *
     * @param volume_max Volume máximo do aparelho.
     */
    public void setVolumeMaximo(int volume_max) {
        this.volume_max = volume_max;
    }

    //Métodos Interface

    /**
     * Devolve o consumo de energia do aparelho.
     *
     * @return Energia consumida pelo aparelho.
     */
    @Override
    public double getConsumoEnergia() {
        return this.getEstado() == Estado.LIGADO ? valor_fixo + 0.5 * this.volume : 0.0;
    }

    public static SmartSpeaker parse(String[] tokens) throws EstadoInvalidoException {
        return new SmartSpeaker(tokens[0],
                SmartDevice.parseEstado(tokens[1]),
                Double.parseDouble(tokens[2]),
                Integer.parseInt(tokens[3]),
                tokens[4],
                Integer.parseInt(tokens[5]));
    }

    /**
     * Clona o aparelho.
     *
     * @return Aparelho clonado.
     */
    @Override
    public SmartSpeaker clone() {
        return new SmartSpeaker(this);
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
        if (!(o instanceof SmartSpeaker speaker)) return false;
        return this.getIdFabricante().equals(speaker.getIdFabricante()) &&
                this.getEstado().equals(speaker.getEstado()) &&
                Double.compare(this.getPrecoInstalacao(), speaker.getPrecoInstalacao()) == 0 &&
                this.volume_max == speaker.volume_max &&
                this.volume == speaker.volume &&
                this.canal.equals(speaker.canal);
    }

    /**
     * Cria um indice através de uma função de hash.
     *
     * @return Indice
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getIdFabricante());
    }

    /**
     * Devolve uma representação textual do objeto.
     *
     * @return Representação do objeto.
     */
    @Override
    public String toString() {
        return super.toString() + "{ Volume: " + this.getVolume() +
                ", Canal: " + this.getCanal() +
                ", Volume Máximo: " + this.getVolumeMaximo() +
                " }";
    }
}