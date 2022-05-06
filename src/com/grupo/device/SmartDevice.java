package com.grupo.device;

import java.util.Locale;

/**
 * Classe SmartDevice
 */
public abstract class SmartDevice {
   //Variáveis de instância
   private String id;
   private Estado estado;
   private double preco_instalacao;

   //Variáveis de classe
   public enum Estado {
       DESLIGADO ,
       LIGADO
   }

   //Construtores

    /**
     * Construtor vazio
     */
    public SmartDevice(){}

    /**
     * Construtor por parâmetros.
     * @param id Identificador do fabricante
     * @param estado Estado atual do aparelho (ligado — desligado)
     * @param preco Preço de instalação definido pelo fabricante.
     */
   public SmartDevice(String id , Estado estado , double preco){
        this.id = id;
        this.estado = estado;
        this.preco_instalacao = preco;
   }

    /**
     * Construtor por cópia.
     * @param device Aparelho a ser copiado.
     */
   public SmartDevice(SmartDevice device){
        this(device.id, device.estado, device.preco_instalacao);
   }

   //Métodos de instância

   //Getters
    /**
     * Devolve o identificador do aparelho emitido pelo fabricante.
     * @return String Identificador.
     */
   public String getIdFabricante(){
       return this.id;
   }

    /**
     * Devolve o estado atual do aparelho.
     * @return Estado do aparelho (ligado — desligado)
     */
   public Estado getEstado(){
       return this.estado;
   }

    /**
     * Devolve o preço de intalação definido pelo fabricante.
     * @return Preço de instalação.
     */
    public double getPrecoInstalacao(){
        return this.preco_instalacao;
    }

    /**
     * Devolve o consumo de energia que do aparelho.
     * @return Energia consumida pelo aparelho.
     */
    public abstract double getConsumoEnergia();

    //Setters

    /**
     * Define o identificador do aparelho.
     * @param id Identificador
     */
    public void setIdFabricante(String id){
        this.id = id;
    }

    /**
     * Define o estado do aparelho.
     * @param estado Estado do aparelho (ligado — desligado).
     */
    public void setEstado(Estado estado){
        if(estado != null) {
          this.estado = estado;
        }
    }

    /**
     * Define o preço de instalação do aparelho.
     * @param preco Preço de instalação.
     */
    public void setPrecoInstalacao(double preco){
        this.preco_instalacao = preco;
    }

    //Interface


    /**
     * Liga um aparelho.
     */
    public void ligar() {
        this.setEstado(Estado.LIGADO);
    }

    /**
     * Desliga um aparelho.
     */
    public void desligar() {
        this.setEstado(Estado.DESLIGADO);
    }

    /**
     * Testa se o aparelho está ligado
     * @return true - false
     */
    public boolean estaLigado(){
        return this.estado == Estado.LIGADO;
    }

    public static Estado parseEstado(String str){
        return str.toUpperCase(Locale.ROOT).equals("LIGADO") ? Estado.LIGADO : Estado.DESLIGADO;
    }

    /**
     * Clona o aparelho.
     * @return Aparelho clonado.
     */
    @Override
    public abstract SmartDevice clone();

    /**
     * Verifica se dois objetos são iguais.
     * @param o Objeto a comparar.
     * @return Verdadeiro ou falso.
     */
    @Override
    public abstract boolean equals(Object o);

    /**
     * Devolve uma representação textual do objeto.
     * @return Representação do objeto.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SmartDevice{");
        sb.append("id='").append(id).append('\'');
        sb.append(", estado=").append(estado);
        sb.append(", preco_instalacao=").append(preco_instalacao);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Cria um indice através de uma função de hash.
     * @return Indice
     */
    @Override
    public abstract int hashCode();
}