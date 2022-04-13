package com.grupo.device;

import java.util.Locale;

/**
 * Classe SmartDevice
 */
public abstract class SmartDevice implements Device {
   //Variáveis de instância
   private String id_Fabricante;
   private Estado estado;
   private int potencia;
   private double preco_instalacao;

   //Variáveis de classe
   private enum Estado {DESLIGADO , LIGADO}

   //Construtores

    /**
     * Construtor vazio
     */
    public SmartDevice(){
        this("null","desligado",0,0);
   }

    /**
     * Construtor por parâmetros.
     * @param id Identificador do fabricante
     * @param estado Estado atual do aparelho (ligado — desligado)
     * @param preco Preço de instalação definido pelo fabricante.
     * @param potencia Potencia do aparelho em watts.
     */
   public SmartDevice(String id , String estado , int potencia , double preco){
        this.setIdFabricante(id);
        this.setEstado(estado);
        this.setPotencia(potencia);
        this.setPrecoInstalacao(preco);
   }

    /**
     * Construtor por cópia.
     * @param device Aparelho a ser copiado.
     */
   public SmartDevice(SmartDevice device){
        this(device.getIdFabricante(), device.getEstado(), device.getPotencia() , device.getPrecoInstalacao());
   }

   //Métodos de instância

   //Getters
    /**
     * Devolve o identificador do aparelho emitido pelo fabricante.
     * @return String Identificador.
     */
    @Override
   public String getIdFabricante(){
       return this.id_Fabricante;
   }

    /**
     * Devolve o estado atual do aparelho.
     * @return Estado do aparelho (ligado — desligado)
     */
   public String getEstado(){
       return this.estado.toString();
   }

    /**
     * Devolve o preço de intalação definido pelo fabricante.
     * @return Preço de instalação.
     */
    public double getPrecoInstalacao(){
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
    private void setIdFabricante(String id){
        this.id_Fabricante = id;
    }

    /**
     * Define o estado do aparelho.
     * @param estado Estado do aparelho (ligado — desligado).
     */
    private void setEstado(String estado){
        if(estado != null) {
            String novo_estado = estado.toUpperCase(Locale.ROOT);
            if (novo_estado.equals(Estado.LIGADO.toString()) || novo_estado.equals(Estado.DESLIGADO.toString())) {
                this.estado = Estado.valueOf(novo_estado);
            }
        }
    }

    /**
     * Define o preço de instalação do aparelho.
     * @param preco Preço de instalação.
     */
    private void setPrecoInstalacao(Double preco){
        this.preco_instalacao = preco;
    }

    /**
     * Define a potência do aparelho.
     * @param potencia Potência.
     */
    private void setPotencia(int potencia){
        this.potencia = potencia;
    }

    //Interface

    /**
     * Devolve o consumo de energia que do aparelho.
     * @return Energia consumida pelo aparelho.
     */
    @Override
    public abstract double getConsumoEnergia();

    /**
     * Liga um aparelho.
     */
    @Override
    public void ligar() {
        this.setEstado("ligado");
    }

    /**
     * Desliga um aparelho.
     */
    @Override
    public void desligar() {
        this.setEstado("desligado");
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
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("SmartDevice = { id_fabricante= ").append(this.getIdFabricante());
        sb.append(" , estado = ").append(this.getEstado().toString());
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
