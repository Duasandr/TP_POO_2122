package com.grupo.device;

import java.util.Objects;


/**
 * Class SmartSpeaker
 */
public class SmartSpeaker extends SmartDevice implements Speaker {
    // Variáveis de instância
    private String canal;
    private int volume;
    private int volume_max;

    //Construtores

    /**
     * Construtor Vazio
     */
    protected SmartSpeaker() {
        this("null", "desligado", 0, 0.0, 0, "null", 100);
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
    protected SmartSpeaker(String id, String estado, int potencia, double preco_instalacao, int volume, String canal, int volume_max) {
        super(id,estado,potencia,preco_instalacao);
        this.setVolumeMaximo(volume_max);
        this.setVolume(volume);
        this.setCanal(canal);
    }

    /**
     * Construtor por cópia
     * @param speaker Aparelho a ser copiado.
     */
    protected SmartSpeaker(SmartSpeaker speaker) {
        this(speaker.getIdFabricante(), speaker.getEstado(), speaker.getPotencia(), speaker.getPrecoInstalacao(), speaker.getVolume(), speaker.getCanal(), speaker.getVolumeMaximo());
    }

    //Métodos de instância

    //Getters
    /**
     * Devolve o volume atual do aparelho selecionado.
     * @return Volume do aparelho.
     */
    public int getVolume() { return this.volume; }

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

    //Setters

    /**
     * Define o volume do aparelho selecionado.
     * @param volume Volume do aparelho.
     */
    private void setVolume(int volume) {
            this.volume = volume;
    }

    /**
     * Define o canal a que está ligado o aparelho.
     * @param canal Canal.
     */
    private void setCanal(String canal) {
        this.canal = canal;
    }

    /**
     * Define o volume máximo do aparelho selecionado.
     * @param volume_max Volume máximo do aparelho.
     */
    private void setVolumeMaximo(int volume_max) {
        this.volume_max = volume_max;
    }

    //Métodos Interface

    /**
     * Devolve o consumo de energia do aparelho.
     * @return Energia consumida pelo aparelho.
     */
    @Override
    public double getConsumoEnergia() {
        return getPotencia() * 0.5 * this.volume;
    }

    /**
     * Muda o canal em que a coluna de rádio está.
     * @param canal Novo canal.
     */
    @Override
    public void mudaCanal(String canal) {
        this.setCanal(canal);
    }

    /**
     * Aumenta o volume da coluna.
     */
    @Override
    public void aumentaVolume() {
        int volume_atual = this.getVolume();
        if (volume_atual < this.getVolumeMaximo()){
            volume_atual++;
            this.setVolume(volume_atual);
        }
    }

    /**
     * Diminui o volume da coluna.
     */
    @Override
    public void diminuiVolume() {
        int volume_atual = this.getVolume();
        if (volume_atual > 0){
            volume_atual--;
            this.setVolume(volume_atual);
        }
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
        if (o == null || !(o.getClass().getSimpleName().equals("SmartSpeaker"))) return false;
        SmartSpeaker speaker = (SmartSpeaker) o;
        return this.getIdFabricante().equals(speaker.getIdFabricante()) &&
                Objects.equals(this.getEstado(), speaker.getEstado()) &&
                this.getPotencia() == speaker.getPotencia() &&
                this.getVolume() == speaker.getVolume() &&
                Double.compare(this.getPrecoInstalacao(),speaker.getPrecoInstalacao()) == 0 &&
                this.getVolumeMaximo() == speaker.getVolumeMaximo() &&
                this.getCanal().equals(speaker.getCanal());
    }

    /**
     * Cria um indice através de uma função de hash.
     * @return Indice
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getIdFabricante(), this.getPrecoInstalacao() , this.getPotencia() , this.getVolumeMaximo());
    }

    /**
     * Devolve uma representação textual do objeto.
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