package com.grupo.device;

import java.util.Objects;

/**
 * Classe SmartDevice
 */
public abstract class SmartDevice {
   //Variáveis de instância
   private String id_Fabricante;
   private boolean estado;
   private double preco_instalacao;
   private int potencia;

   //Construtores

    /**
     * Construtor vazio
     */
    public SmartDevice(){
        this("null",false,0.0);
   }

    /**
     * Construtor por parâmetros.
     * @param id Identificador do fabricante
     * @param estado Estado atual do aparelho (ligado — desligado)
     * @param preco Preço de instalação definido pelo fabricante.
     */
   public SmartDevice(String id , boolean estado , Double preco){
        this.setIdFabricante(id);
        this.setEstado(estado);
        this.setPrecoInstalacao(preco);
   }

    /**
     * Construtor por cópia.
     * @param device Aparelho a ser copiado.
     */
   public SmartDevice(SmartDevice device){
        this(device.getIdFabricante(), device.getEstado(), device.getPrecoInstalacao());
   }

   //Métodos de instância

   //Getters
    /**
     * Devolve o identificador do aparelho emitido pelo fabricante.
     * @return String Identificador.
     */
   public String getIdFabricante(){
       return this.id_Fabricante;
   }

    /**
     * Devolve o estado atual do aparelho.
     * @return Estado do aparelho (ligado — desligado)
     */
   public Boolean getEstado(){
       return this.estado;
   }

    /**
     * Devolve o preço de intalação definido pelo fabricante.
     * @return Preço de instalação.
     */
    public Double getPrecoInstalacao(){
        return this.preco_instalacao;
    }

    /**
     * Devolve a pot~encia do aparelho. (kw)
     * @return Potência.
     */
    public int getPotencia(){return this.potencia;}

    //Setters

    /**
     * Define o identificador do aparelho.
     * @param id Identificador
     */
    protected void setIdFabricante(String id){
        this.id_Fabricante = id;
    }

    /**
     * Define o estado do aparelho.
     * @param estado Estado do aparelho (ligado — desligado).
     */
    public void setEstado(Boolean estado){
        this.estado = estado;
    }

    /**
     * Define o preço de instalação do aparelho.
     * @param preco Preço de instalação.
     */
    protected void setPrecoInstalacao(Double preco){
        this.preco_instalacao = preco;
    }

    /**
     * Define a potência do aparelho.
     * @param potencia Potência.
     */
    protected void setPotencia(int potencia){
        this.potencia = potencia;
    }

    /**
     * Devolve o consumo de energia que do aparelho.
     * @return Energia consumida pelo aparelho.
     */
    public abstract Double consumoEnergia();

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
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("SmartDevice = { id_fabricante= ").append(this.getIdFabricante());
        sb.append(" , estado = ").append(this.getEstado());
        sb.append(" , preco_instalacao = ").append(this.getPrecoInstalacao());
        sb.append(" , potencia = ").append(this.getPotencia());
        sb.append("}");
        return sb.toString();
    }

    /**
     * Cria um indice através de uma função de hash.
     * @return Indice
     */
    @Override
    public abstract int hashCode();
}
