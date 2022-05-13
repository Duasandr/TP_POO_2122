package com.grupo.device;

import exceptions.EstadoInvalidoException;

import java.io.Serializable;
import java.util.Objects;

public class SmartCamera extends SmartDevice implements Serializable {
    //Variáveis de instância
    private int resolucao;
    private double tamanho_ficheiro;

    //Construtores

    /**
     * Construtor vazio
     */
    protected SmartCamera() {

    }

    /**
     * Construtor por paâmetros
     *
     * @param id               Identificador do dispositivo
     * @param estado           Estado do dispositivo
     * @param preco_instalacao Preço de instalação
     * @param res              Resolução da imagem
     * @param tamanho_ficheiro Tamanho do ficheiro de gravação
     */
    protected SmartCamera(String id, Estado estado, double preco_instalacao, int res, double tamanho_ficheiro) {
        super(id, estado, preco_instalacao);
        this.resolucao = res;
        this.tamanho_ficheiro = tamanho_ficheiro;
    }

    /**
     * Construtor por cópia.
     *
     * @param cam Camera a copiar
     */
    protected SmartCamera(SmartCamera cam) {
        this(cam.getIdFabricante(), cam.getEstado(), cam.getPrecoInstalacao(), cam.resolucao, cam.tamanho_ficheiro);
    }

    //Métodos de instância

    //Getters

    /**
     * Devolve a resolução de imagem.
     *
     * @return double Resolução.
     */
    public int getResolucao() {
        return this.resolucao;
    }

    /**
     * Devolve o tamanho do ficheiro de gravação.
     *
     * @return double Tamanho do ficheiro.
     */
    public double getTamanhoFicheiro() {
        return this.tamanho_ficheiro;
    }

    //Setters

    /**
     * Define a resolução de imagem.
     *
     * @param res resolução de imagem.
     */
    public void setResolucao(int res) {
        this.resolucao = res;
    }

    /**
     * Define o tamanho do ficheiro de gravação.
     *
     * @param tamanho_ficheiro tamanho do ficheiro.
     */
    public void setTamanhoFicheiro(Double tamanho_ficheiro) {
        this.tamanho_ficheiro = tamanho_ficheiro;
    }

    /**
     * Devolve o consumo de energia do aparelho.
     *
     * @return double Consumo de energia em watts
     */
    @Override
    public double getConsumoEnergia() {
        double consumo = 0.0;
        if (this.getEstado() == Estado.LIGADO) {
            consumo = this.tamanho_ficheiro * this.resolucao;
        }
        return consumo;
    }

    /**
     * Transforma uma String formatada numa SmartCamera.
     *
     * @param tokens String.
     * @return SmartCamera.
     */
    public static SmartCamera parse(String[] tokens) throws EstadoInvalidoException {
        return new SmartCamera(tokens[0],
                SmartDevice.parseEstado(tokens[1]),
                Double.parseDouble(tokens[2]),
                Integer.parseInt(tokens[3]),
                Double.parseDouble(tokens[4]));
    }

    /**
     * Devolve uma cópia da camera.
     *
     * @return Cópia da camera.
     */
    @Override
    public SmartCamera clone() {
        return new SmartCamera(this);
    }

    /**
     * Compara um objeto com a camera.
     *
     * @param o Objeto a comparar.
     * @return true , false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartCamera cam)) return false;
        return this.getIdFabricante().equals(cam.getIdFabricante()) &&
                Objects.equals(this.getEstado(), cam.getEstado()) &&
                Double.compare(this.getPrecoInstalacao(), cam.getPrecoInstalacao()) == 0 &&
                Objects.equals(this.resolucao, cam.resolucao) &&
                Double.compare(this.tamanho_ficheiro, cam.tamanho_ficheiro) == 0;
    }

    /**
     * Representação textual do objeto.
     *
     * @return String
     */
    @Override
    public String toString() {
        return "SmartCamera{" + "ID=" + this.getIdFabricante() +
                ", estado=" + this.getEstado().toString() +
                ", preco_instalacao=" + this.getPrecoInstalacao() +
                ", resolucao=" + this.resolucao +
                ", tamanho_ficheiro=" + this.tamanho_ficheiro +
                "}";
    }

    /**
     * Devolve um hascode associado ao objeto.
     *
     * @return int HashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getIdFabricante());
    }
}