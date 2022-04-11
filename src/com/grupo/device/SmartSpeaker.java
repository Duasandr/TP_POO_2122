package com.grupo.device;

import java.util.Objects;


/**
 * Class SmartSpeaker
 */
public class SmartSpeaker extends SmartDevice {
    // Variáveis de instância
    private int volume;
    private String canal;
    private int volume_max;

    //Construtores

    /**
     * Construtor Vazio
     */
    protected SmartSpeaker() {
        this("null", false, 0, 0.0, 0, "null", 0);
    }

    /**
     * Construtor por parâmetros
     *
     * @param id Identificador do fabricante.
     * @param estado Estado atual do aparelho (ligado - desligado).
     * @param potencia Potência de consumo do aparelho.
     * @param preco_instalacao Preço de instalação definido pelo fabricante.
     * @param volume Estado atual do volume do aparelho.
     * @param canal Canal atual do aparelho.
     * @param volume_max Volume máximo do aparelho.
     */
    protected SmartSpeaker(String id, boolean estado, int potencia, double preco_instalacao, int volume, String canal, int volume_max) {
        this.setIdFabricante(id);
        this.setEstado(estado);
        this.setPotencia(potencia);
        this.setPrecoInstalacao(preco_instalacao);
        this.setVolume(volume);
        this.setCanal(canal);
        this.setVolumeMaximo(volume_max);
    }

    /**
     * Construtor por cópia
     * @param speaker Aparelho a ser copiado.
     */
    protected SmartSpeaker(SmartSpeaker speaker) {
        this.volume = speaker.getVolume();
        this.canal = speaker.getCanal();
        this.volume_max = speaker.getVolumeMaximo();
    }

    //Métodos de instância

    //Getters
    /**
     * Devolve o volume atual do aparelho selecionado.
     * @return Volume do aparelho.
     */
    public int getVolume() {
        return this.volume;
    }

    /**
     * Devolve o canal que está a tocar no aparelho.
     * @return Canal.
     */
    public String getCanal() {
        return this.canal;
    }

    /**
     * Devolve o volume máximo do aparelho selecionado.
     * @return Volume máximo.
     */
    public int getVolumeMaximo() {
        return this.volume_max;
    }

    /**
     * Define o volume do aparelho selecionado.
     * @param volume Volume do aparelho.
     */
    public void setVolume(int volume) {
        this.volume = volume;
    }

    /**
     * Define o canal a que está ligado o aparelho.
     * @param canal Canal.
     */
    public void setCanal(String canal) {
        this.canal = canal;
    }

    /**
     * Define o volume máximo do aparelho selecionado.
     * @param volume_max Volume máximo do aparelho.
     */
    protected void setVolumeMaximo(int volume_max) {
        this.volume_max = volume_max;
    }

    /**
     * Devolve o consumo de energia do aparelho.
     * @return Energia consumida pelo aparelho.
     */
    @Override
    public Double consumoEnergia() {
        return null;
    }

    /**
     * Clona o aparelho.
     * @return Aparelho clonado.
     */
    @Override
    public SmartSpeaker clone() {
        return new SmartSpeaker(this);
    }

    /**
     * Verifica se dois objetos são iguais.
     * @param o Objeto a comparar.
     * @return Verdadeiro ou Falso.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartSpeaker speaker = (SmartSpeaker) o;
        return speaker.getVolume() == this.getVolume() &&
                speaker.getCanal().equals(this.getCanal()) &&
                speaker.getVolumeMaximo() == this.getVolumeMaximo();
    }

    /**
     * Cria um indice através de uma função de hash.
     * @return Indice
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), volume, canal, volume_max);
    }

    /**
     * Devolve uma representação textual do objeto.
     * @return Representação do objeto.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ Volume: " + this.getVolume());
        sb.append(", Canal: " + this.getCanal());
        sb.append(", Volume Máximo: " + this.getVolumeMaximo());
        sb.append(" }");
        return sb.toString();
    }
}