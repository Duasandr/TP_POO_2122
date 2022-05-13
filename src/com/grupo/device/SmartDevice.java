package com.grupo.device;

import exceptions.EstadoInvalidoException;
import exceptions.SmartDeviceInvalidoException;
import exceptions.TonalidadeInvalidaException;

import java.io.Serializable;
import java.util.Locale;

/**
 * Classe SmartDevice
 */
public abstract class SmartDevice implements Serializable {
    //Variáveis de instância
    private String id;
    private Estado estado;
    private double preco_instalacao;

    //Variáveis de classe
    public enum Estado {
        DESLIGADO,
        LIGADO
    }

    //Construtores

    /**
     * Construtor vazio
     */
    public SmartDevice() {
    }

    /**
     * Construtor por parâmetros.
     *
     * @param id     Identificador do fabricante
     * @param estado Estado atual do aparelho (ligado — desligado)
     * @param preco  Preço de instalação definido pelo fabricante.
     */
    public SmartDevice(String id, Estado estado, double preco) {
        this.id = id;
        this.estado = estado;
        this.preco_instalacao = preco;
    }

    //Métodos de instância

    //Getters

    /**
     * Devolve o identificador do aparelho emitido pelo fabricante.
     *
     * @return String Identificador.
     */
    public String getIdFabricante() {
        return this.id;
    }

    /**
     * Devolve o estado atual do aparelho.
     *
     * @return Estado do aparelho (ligado — desligado)
     */
    public Estado getEstado() {
        return this.estado;
    }

    /**
     * Devolve o preço de intalação definido pelo fabricante.
     *
     * @return Preço de instalação.
     */
    public double getPrecoInstalacao() {
        return this.preco_instalacao;
    }

    /**
     * Devolve o consumo de energia que do aparelho.
     *
     * @return Energia consumida pelo aparelho.
     */
    public abstract double getConsumoEnergia();

    //Setters

    /**
     * Define o identificador do aparelho.
     *
     * @param id Identificador
     */
    public void setIdFabricante(String id) {
        this.id = id;
    }

    /**
     * Define o estado do aparelho.
     *
     * @param estado Estado do aparelho (ligado — desligado).
     */
    public void setEstado(Estado estado) {
        if (estado != null) {
            this.estado = estado;
        }
    }

    /**
     * Define o preço de instalação do aparelho.
     *
     * @param preco Preço de instalação.
     */
    public void setPrecoInstalacao(double preco) {
        this.preco_instalacao = preco;
    }

    //Métodos de parsing

    /**
     * Faz o parse de uma 'string' para o estado do aparelho.
     * @param str Estado em forma de texto
     * @return Estado
     */
    public static Estado parseEstado(String str) throws EstadoInvalidoException {
        return switch (str.toUpperCase(Locale.ROOT)){
            case "LIGADO"-> Estado.LIGADO;
            case "DESLIGADO"-> Estado.DESLIGADO;
            default -> throw new EstadoInvalidoException(str);
        };
    }

    /**
     * Faz o parse de uma lista de tokens para um SmartDevice.
     * @param tokens Parâmetros do objeto.
     * @return Uma nova instância do objeto.
     * @throws SmartDeviceInvalidoException Acontece quando não é reconhecido o tipo do dispositivo.
     * @throws TonalidadeInvalidaException Acontece quando não é reconhecida uma tonalidade.
     */
    public static SmartDevice parse(String[] tokens) throws SmartDeviceInvalidoException, TonalidadeInvalidaException, EstadoInvalidoException {
        return switch (tokens[0]) {
            case "Bulb" -> SmartBulb.parse(tokens[1].split(";"));
            case "Speaker" -> SmartSpeaker.parse(tokens[1].split(";"));
            case "Camera" -> SmartCamera.parse(tokens[1].split(";"));
            default -> throw new SmartDeviceInvalidoException(tokens[0]);
        };
    }

    //Métodos de Object

    /**
     * Clona o aparelho.
     *
     * @return Aparelho clonado.
     */
    @Override
    public abstract SmartDevice clone();

    /**
     * Verifica se dois objetos são iguais.
     *
     * @param o Objeto a comparar.
     * @return Verdadeiro ou falso.
     */
    @Override
    public abstract boolean equals(Object o);

    /**
     * Cria um indice através de uma função de hash.
     *
     * @return Indice
     */
    @Override
    public abstract int hashCode();
}