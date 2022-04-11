package com.grupo.device;

import java.util.Objects;

/**
 * Classe SmartDevice
 */
public abstract class SmartDevice {
   //Variáveis de instância
   // ...

   //Construtores

    /**
     * Construtor vazio
     */
    public SmartDevice(){
        //...
   }

    /**
     * Construtor por parâmetros.
     * @param id Identificador do fabricante
     * @param estado Estado atual do aparelho (ligado — desligado)
     * @param preco Preço de instalação definido pelo fabricante.
     */
   public SmartDevice(String id , boolean estado , Double preco){
        //...
   }

    /**
     * Construtor por cópia.
     * @param device Aparelho a ser copiado.
     */
   public SmartDevice(SmartDevice device){
        //...
   }

   //Métodos de instância

   //Getters
    /**
     * Devolve o identificador do aparelho emitido pelo fabricante.
     * @return String Identificador.
     */
   public String getIdFabricante(){
       return null;
   }

    /**
     * Devolve o estado atual do aparelho.
     * @return Estado do aparelho (ligado — desligado)
     */
   public Boolean getEstado(){
       return false;
   }

    /**
     * Devolve o preço de intalação definido pelo fabricante.
     * @return Preço de instalação.
     */
    public Double getPrecoInstalacao(){
        return 0.0;
    }


    //Setters

    /**
     * Define o identificador do aparelho.
     * @param id Identificador
     */
    protected void setIdFabricante(String id){
        //...
    }

    /**
     * Define o estado do aparelho.
     * @param estado Estado do aparelho (ligado — desligado).
     */
    public void setEstado(Boolean estado){
        //...
    }

    /**
     * Define o preço de instalação do aparelho.
     * @param preco Preço de instalação.
     */
    protected void setPrecoInstalacao(Double preco){
        //...
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
    public boolean equals(Object o){
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmartDevice device = (SmartDevice) o;
        return device.getIdFabricante().equals(this.getIdFabricante()) &&
                device.getEstado() == this.getEstado() &&
                device.getPrecoInstalacao().equals(this.getPrecoInstalacao());
    }

    /**
     * Devolve uma representação textual do objeto.
     * @return Representação do objeto.
     */
    @Override
    public String toString(){
        return null;
    }

    /**
     * Cria um indice através de uma função de hash.
     * @return Indice
     */
    @Override
    public int hashCode(){
        return Objects.hash(this.getIdFabricante());
    }
}
