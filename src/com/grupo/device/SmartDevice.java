package com.grupo.device;

import com.grupo.exceptions.EstadoInvalidoException;
import com.grupo.exceptions.SmartDeviceInvalidoException;
import com.grupo.exceptions.TonalidadeInvalidaException;

import java.io.Serializable;
import java.util.Locale;

/**
 * Classe SmartDevice
 */
public abstract
class SmartDevice implements Serializable {
    private String id;
    private Estado estado;
    private double preco_instalacao;

    /**
     * Construtor vazio
     */
    public
    SmartDevice ( ) {
    }

    /**
     * Construtor por parâmetros.
     *
     * @param id     Identificador do fabricante
     * @param estado Estado atual do aparelho (ligado — desligado)
     * @param preco  Preço de instalação definido pelo fabricante.
     */
    public
    SmartDevice ( String id , Estado estado , double preco ) {
        this.id               = id;
        this.estado           = estado;
        this.preco_instalacao = preco;
    }

    /**
     * Faz o parse de uma 'string' para o estado do aparelho.
     *
     * @param str Estado em forma de texto
     * @return Estado
     */
    public static
    Estado parseEstado ( String str ) throws EstadoInvalidoException {
        return switch ( str.toUpperCase ( Locale.ROOT ) ) {
            case "LIGADO" -> Estado.LIGADO;
            case "DESLIGADO" -> Estado.DESLIGADO;
            default -> throw new EstadoInvalidoException ( str );
        };
    }

    /**
     * Faz o parse de uma lista de tokens para um SmartDevice.
     *
     * @param tokens Parâmetros do objeto.
     * @return Uma nova instância do objeto.
     * @throws SmartDeviceInvalidoException Acontece quando não é reconhecido o tipo do dispositivo.
     * @throws TonalidadeInvalidaException Acontece quando não é reconhecida uma tonalidade.
     */
    public static
    SmartDevice parse ( String[] tokens ) throws SmartDeviceInvalidoException, TonalidadeInvalidaException, EstadoInvalidoException {
        return switch ( tokens[ 0 ] ) {
            case "Bulb" -> SmartBulb.parse ( tokens[ 1 ].split ( ";" ) );
            case "Speaker" -> SmartSpeaker.parse ( tokens[ 1 ].split ( ";" ) );
            case "Camera" -> SmartCamera.parse ( tokens[ 1 ].split ( ";" ) );
            default -> throw new SmartDeviceInvalidoException ( tokens[ 0 ] );
        };
    }

    /**
     * Devolve o identificador do aparelho emitido pelo fabricante.
     *
     * @return String Identificador.
     */
    public
    String getIdFabricante ( ) {
        return this.id;
    }

    /**
     * Define o identificador do aparelho.
     *
     * @param id Identificador
     */
    public
    void setIdFabricante ( String id ) {
        this.id = id;
    }

    /**
     * Devolve o estado atual do aparelho.
     *
     * @return Estado do aparelho (ligado — desligado)
     */
    public
    Estado getEstado ( ) {
        return this.estado;
    }

    /**
     * Define o estado do aparelho.
     *
     * @param estado Estado do aparelho (ligado — desligado).
     */
    public
    void setEstado ( Estado estado ) {
        if ( estado != null ) {
            this.estado = estado;
        }
    }

    /**
     * Devolve o preço de intalação definido pelo fabricante.
     *
     * @return Preço de instalação.
     */
    public
    double getPrecoInstalacao ( ) {
        return this.preco_instalacao;
    }

    /**
     * Define o preço de instalação do aparelho.
     *
     * @param preco Preço de instalação.
     */
    public
    void setPrecoInstalacao ( double preco ) {
        this.preco_instalacao = preco;
    }

    /**
     * Devolve o consumo de energia que do aparelho.
     *
     * @return Energia consumida pelo aparelho.
     */
    public abstract
    double getConsumoEnergia ( );

    /**
     * Clona o aparelho.
     *
     * @return Aparelho clonado.
     */
    @Override
    public abstract
    SmartDevice clone ( );

    //Métodos de Object

    /**
     * Verifica se dois objetos são iguais.
     *
     * @param o Objeto a comparar.
     * @return Verdadeiro ou falso.
     */
    @Override
    public abstract
    boolean equals ( Object o );

    /**
     * Cria um indice através de uma função de hash.
     *
     * @return Indice
     */
    @Override
    public abstract
    int hashCode ( );

    /**
     * Estados possíveis que um dispositivo pode ter.
     */
    public
    enum Estado {
        DESLIGADO, LIGADO
    }
}