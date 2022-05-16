package com.grupo.device;

import com.grupo.exceptions.EstadoInvalidoException;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public
class SmartCamera extends SmartDevice {

    private int    resolucao;
    private double tamanho_ficheiro;



    /**
     * Construtor vazio
     */
    public
    SmartCamera ( ) {

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
    public
    SmartCamera ( String id , Estado estado , double preco_instalacao , int res , double tamanho_ficheiro ) {
        super ( id , estado , preco_instalacao );
        this.resolucao        = res;
        this.tamanho_ficheiro = tamanho_ficheiro;
    }

    /**
     * Construtor por cópia.
     *
     * @param cam Camera a copiar
     */
    public
    SmartCamera ( SmartCamera cam ) {
        this ( cam.getIdFabricante ( ) , cam.getEstado ( ) , cam.getPrecoInstalacao ( ) , cam.resolucao , cam.tamanho_ficheiro );
    }



    /**
     * Transforma uma String formatada numa SmartCamera.
     *
     * @param tokens String.
     * @return SmartCamera.
     */
    public static
    SmartCamera parse ( String[] tokens ) throws EstadoInvalidoException {
        return new SmartCamera ( tokens[ 0 ] ,
                                 SmartDevice.parseEstado ( tokens[ 1 ] ) ,
                                 Double.parseDouble ( tokens[ 2 ] ) ,
                                 Integer.parseInt ( tokens[ 3 ] ) ,
                                 Double.parseDouble ( tokens[ 4 ] ) );
    }

    /**
     * Devolve a resolução de imagem.
     *
     * @return double Resolução.
     */
    public
    int getResolucao ( ) {
        return this.resolucao;
    }

    //Setters

    /**
     * Define a resolução de imagem.
     *
     * @param res resolução de imagem.
     */
    public
    void setResolucao ( int res ) {
        this.resolucao = res;
    }

    /**
     * Devolve o tamanho do ficheiro de gravação.
     *
     * @return double Tamanho do ficheiro.
     */
    public
    double getTamanhoFicheiro ( ) {
        return this.tamanho_ficheiro;
    }

    /**
     * Define o tamanho do ficheiro de gravação.
     *
     * @param tamanho_ficheiro tamanho do ficheiro.
     */
    public
    void setTamanhoFicheiro ( Double tamanho_ficheiro ) {
        this.tamanho_ficheiro = tamanho_ficheiro;
    }

    /**
     * Devolve o consumo de energia do aparelho.
     *
     * @return double Consumo de energia em watts
     */
    @Override
    public
    double getConsumoEnergia ( ) {
        double consumo = 0.0;
        if ( this.getEstado ( ) == Estado.LIGADO ) {
            consumo = this.tamanho_ficheiro * this.resolucao * 0.001;
        }
        return consumo;
    }

    /**
     * Devolve uma cópia da camera.
     *
     * @return Cópia da camera.
     */
    @Override
    public
    SmartCamera clone ( ) {
        return new SmartCamera ( this );
    }

    /**
     * Compara um objeto com a camera.
     *
     * @param o Objeto a comparar.
     * @return true , false
     */
    @Override
    public
    boolean equals ( Object o ) {
        if ( this == o ) return true;
        if ( ! ( o instanceof SmartCamera cam ) ) return false;
        return this.getIdFabricante ( ).equals ( cam.getIdFabricante ( ) ) &&
                Objects.equals ( this.getEstado ( ) , cam.getEstado ( ) ) &&
                Double.compare ( this.getPrecoInstalacao ( ) , cam.getPrecoInstalacao ( ) ) == 0 &&
                Objects.equals ( this.resolucao , cam.resolucao ) &&
                Double.compare ( this.tamanho_ficheiro , cam.tamanho_ficheiro ) == 0;
    }

    /**
     * Representação textual do objeto.
     *
     * @return String
     */
    @Override
    public
    String toString ( ) {
        return new StringJoiner ( ", " , SmartCamera.class.getSimpleName ( ) + "[" , "]" )
                .add ( "ID='" + this.getIdFabricante ( ) + "'" )
                .add ( "estado=" + this.getEstado ( ).toString ( ) )
                .add ( "preco_instalacao=" + getPrecoInstalacao ( ) )
                .add ( "resolucao=" + resolucao )
                .add ( "tamanho_ficheiro=" + tamanho_ficheiro )
                .toString ( );
    }

    /**
     * Devolve um hascode associado ao objeto.
     *
     * @return int HashCode
     */
    @Override
    public
    int hashCode ( ) {
        return Objects.hash ( this.getIdFabricante ( ) );
    }
}