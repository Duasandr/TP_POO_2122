package com.grupo.house;

import com.grupo.device.SmartDevice;
import com.grupo.exceptions.*;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

public
class Divisao implements Serializable {
    private String                      nome;
    private Map < String, SmartDevice > aparelhos;

    /**
     * Construtor vazio
     */
    public
    Divisao ( ) {
        this.aparelhos = new HashMap <> ( );
    }

    /**
     * Construtor por parâmetros.
     *
     * @param nome      Nome da divisão.
     * @param aparelhos Aparelhos presentes na divisão.
     */
    public
    Divisao ( String nome , Collection < SmartDevice > aparelhos ) {
        this ( );
        this.nome = nome;
        this.setDevices ( aparelhos );
    }

    /**
     * Construtor por parâmetros.
     *
     * @param nome Nome da divisão.
     */
    public
    Divisao ( String nome ) {
        this ( );
        this.nome = nome;
    }

    /**
     * Construtor por cópia.
     *
     * @param divisao Divisão a copiar.
     */
    public
    Divisao ( Divisao divisao ) {
        this ( divisao.nome , divisao.aparelhos.values ( ) );
    }

    /**
     * Transforma uma linha de texto formatada numa nova divisão.
     *
     * @param str String formatada
     * @return Divisao nova
     * @throws LinhaFormatadaInvalidaException Acontece quando a linha de texto está mal formatada.
     */
    public static
    Divisao parse ( String str ) throws LinhaFormatadaInvalidaException, SmartDeviceInvalidoException, TonalidadeInvalidaException, EstadoInvalidoException {
        String[] tokens  = str.split ( "\\[" );
        Divisao  divisao = new Divisao ( );

        if ( tokens.length >= 1 ) {
            divisao.setNome ( tokens[ 0 ] );
            Set < SmartDevice > dispositivos = new HashSet <> ( );

            if ( tokens.length == 2 ) {
                String[] str_dispositivos = tokens[ 1 ].split ( " " );
                if ( tokens[ 1 ].length ( ) > 1 ) {
                    for (String disp : str_dispositivos) {
                        dispositivos.add ( SmartDevice.parse ( disp.split ( ":" ) ) );
                    }
                }
            }
            divisao.setDevices ( dispositivos );
        }
        else {
            throw new LinhaFormatadaInvalidaException ( str );
        }

        return divisao;
    }

    /**
     * Devolve o nome da divisão.
     *
     * @return Nome da divisão.
     */
    public
    String getNome ( ) {
        return this.nome;
    }

    //Getters

    /**
     * Define o nome da divisão.
     *
     * @param nome Nome da divisão.
     */
    public
    void setNome ( String nome ) {
        this.nome = nome;
    }

    /**
     * Devolve um conjunto com os aparelhos da divisão.
     *
     * @return Conjunto de aparelhos.
     */
    public
    Set < SmartDevice > getDevices ( ) {
        Set < SmartDevice > copia = new HashSet <> ( this.aparelhos.size ( ) );
        for (SmartDevice aparelho : this.aparelhos.values ( )) {
            copia.add ( aparelho.clone ( ) );
        }
        return copia;
    }

    /**
     * Define os aparelhos presentes na divisão.
     *
     * @param aparelhos Aparelhos a inserir.
     */
    public
    void setDevices ( Collection < SmartDevice > aparelhos ) {
        if ( aparelhos != null ) {
            HashMap < String, SmartDevice > clone = new HashMap <> ( aparelhos.size ( ) );

            for (SmartDevice aparelho : aparelhos) {
                clone.put ( aparelho.getIdFabricante ( ) , aparelho.clone ( ) );
            }
            this.aparelhos = clone;
        }
    }

    /**
     * Devolve o consuma de energia da divisão.
     *
     * @return Consumo de energia.
     */
    public
    double getConsumoEnergia ( ) {
        double consumo = 0.0;
        for (SmartDevice device : this.aparelhos.values ( )) {
            consumo += device.getConsumoEnergia ( );
        }
        return consumo;
    }

    /**
     * Devolve o número de dispositivos da divisão.
     *
     * @return int
     */
    public
    int getNumeroDispositivos ( ) {
        return this.aparelhos.size ( );
    }

    /**
     * Devolve um SmartDevice
     *
     * @param id String
     * @return SmartDevice
     * @throws DispositivoNaoExisteException Quando o dispositivo não existe
     */
    public
    SmartDevice getDispositivo ( String id ) throws DispositivoNaoExisteException {
        if ( this.aparelhos.containsKey ( id ) ) {
            return this.aparelhos.get ( id ).clone ( );
        }
        else {
            throw new DispositivoNaoExisteException ( id );
        }
    }

    /**
     * Verifica se um aparelho existe na divisão.
     *
     * @param id_dispositivo Identificador do aparelho.
     * @return true - false
     */
    public
    boolean contem ( String id_dispositivo ) {
        return this.aparelhos.containsKey ( id_dispositivo );
    }

    /**
     * Altera todos os estados de todos os dispositivos da divisão.
     *
     * @param acao Ação a executar.
     */
    public
    void foreachDispositivo ( Consumer < SmartDevice > acao ) {
        for (SmartDevice aparelho : this.aparelhos.values ( )) {
            acao.accept ( aparelho );
        }
    }

    /**
     * Altera o estado de um dispositivo em específico.
     *
     * @param id_dispositivo Identificador do dispositivo.
     * @param acao           Ação a executar.
     * @throws DispositivoNaoExisteException Acontece quando não existe um dispositivo.
     */
    public
    void alteraDispositivo ( String id_dispositivo , Consumer < SmartDevice > acao ) throws DispositivoNaoExisteException {
        if ( this.aparelhos.containsKey ( id_dispositivo ) ) {
            acao.accept ( this.aparelhos.get ( id_dispositivo ) );
        }
        else {
            throw new DispositivoNaoExisteException ( id_dispositivo );
        }
    }

    /**
     * Remove um dispositivo da divisão.
     *
     * @param id_dispositivo String
     * @return SmartDevice
     * @throws DispositivoNaoExisteException Quando o dispositivo não existe.
     */
    public
    SmartDevice removeDispositivo ( String id_dispositivo ) throws DispositivoNaoExisteException {
        if ( this.aparelhos.containsKey ( id_dispositivo ) ) {
            return this.aparelhos.remove ( id_dispositivo );
        }
        else {
            throw new DispositivoNaoExisteException ( id_dispositivo );
        }
    }

    /**
     * Adiciona um dispositivo à divisão.
     *
     * @param device SmartDevice
     */
    public
    void adicionaDispositivo ( SmartDevice device ) {
        this.aparelhos.put ( device.getIdFabricante ( ) , device.clone ( ) );
    }

    /**
     * Devolve uma cópia da divisão.
     *
     * @return Cópia da divisão.
     */
    @Override
    public
    Divisao clone ( ) {
        return new Divisao ( this );
    }

    /**
     * Representação textual da divisão.
     *
     * @return String.
     */
    @Override
    public
    String toString ( ) {
        return new StringJoiner ( ", " , Divisao.class.getSimpleName ( ) + "[" , "]" )
                .add ( "nome='" + nome + "'" )
                .add ( "aparelhos=" + aparelhos )
                .toString ( );
    }

    /**
     * Testa se uma divisão é igual a um objeto.
     *
     * @param o Objeto a comparar.
     * @return true - false
     */
    @Override
    public
    boolean equals ( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass ( ) != o.getClass ( ) ) return false;
        Divisao divisao = ( Divisao ) o;
        return Objects.equals ( nome , divisao.nome ) && Objects.equals ( aparelhos , divisao.aparelhos );
    }

    /**
     * Devolve um indíce através de uma função hash.
     *
     * @return Índice.
     */
    @Override
    public
    int hashCode ( ) {
        return Objects.hash ( nome );
    }
}
