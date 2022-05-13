package com.grupo.device;

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
     * @param str String.
     * @return SmartCamera.
     */
    public static SmartCamera parse(String str) {
        String[] tokens = str.split(";");
        SmartCamera camera = new SmartCamera();
        camera.setIdFabricante(tokens[0]);
        camera.setEstado(SmartDevice.parseEstado(tokens[1]));
        camera.setPrecoInstalacao(Double.parseDouble(tokens[2]));
        camera.resolucao = Integer.parseInt(tokens[3]);
        camera.tamanho_ficheiro = Double.parseDouble(tokens[4]);

        return camera;
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
        if (!(o instanceof SmartCamera)) return false;
        SmartCamera that = (SmartCamera) o;
        return resolucao == that.resolucao && Double.compare(that.tamanho_ficheiro, tamanho_ficheiro) == 0;
    }

    /**
     * Representação textual do objeto.
     *
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SmartCamera{");
        sb.append("resolucao=").append(resolucao);
        sb.append(", tamanho_ficheiro=").append(tamanho_ficheiro);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Devolve um hascode associado ao objeto.
     *
     * @return int HashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(resolucao, tamanho_ficheiro);
    }
}