package com.grupo.house;

import com.grupo.device.SmartDevice;
import com.grupo.exceptions.*;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;


public
class Casa implements Serializable {
    private String                  morada;
    private String                  proprietario;
    private String                  fornecedor;
    private int                     nif_proprietario;
    private Map < String, Divisao > divisoes;
    private long                    ultima_fatura;

    /**
     * Construtor vazio.
     */
    public
    Casa ( ) {
    }

    /**
     * Construtor por parâmetros
     *
     * @param proprietario     Nome do proprietário.
     * @param nif_proprietario Número de identificação fiscal do proprietário.
     * @param divisoes         Divisões da casa.
     */
    public
    Casa ( String proprietario , String morada , int nif_proprietario , Collection < Divisao > divisoes , String fornecedor , long fatura ) {
        this.proprietario     = proprietario;
        this.nif_proprietario = nif_proprietario;
        this.fornecedor       = fornecedor;
        this.morada           = morada;
        this.divisoes         = new HashMap <> ( );
        this.ultima_fatura    = fatura;
        this.setDivisoes ( divisoes );
    }

    /**
     * Construtor por cópia.
     *
     * @param casa Casa
     */
    public
    Casa ( Casa casa ) {
        this ( casa.proprietario , casa.morada , casa.nif_proprietario , casa.divisoes.values ( ) , casa.fornecedor , casa.ultima_fatura );
    }

    /**
     * Transforma uma String nima Casa.
     *
     * @param str String
     * @return Casa
     * @throws LinhaFormatadaInvalidaException Quando a linha está mal formatada.
     * @throws SmartDeviceInvalidoException    Quando o SmartDevice está mal formatado.
     * @throws TonalidadeInvalidaException     Quando a Tonalidade é inválida.
     * @throws EstadoInvalidoException         Quando o estado é inválido.
     */
    public static
    Casa parse ( String str ) throws LinhaFormatadaInvalidaException, SmartDeviceInvalidoException, TonalidadeInvalidaException, EstadoInvalidoException {
        String[] tokens          = str.split ( "\\{" );
        String[] tokens_casa     = tokens[ 0 ].split ( ";" );
        String[] tokens_divisoes = tokens[ 1 ].split ( "\\|" );
        Casa     casa            = new Casa ( );

        if ( tokens.length == 4 ) {
            casa.morada           = tokens_casa[ 0 ];
            casa.proprietario     = tokens_casa[ 1 ];
            casa.fornecedor       = tokens_casa[ 2 ];
            casa.nif_proprietario = Integer.parseInt ( tokens_casa[ 3 ] );

            Set < Divisao > divisoes = new HashSet <> ( );

            for (String divisao : tokens_divisoes) {
                divisoes.add ( Divisao.parse ( divisao ) );
            }

            casa.setDivisoes ( divisoes );
        }
        else {
            throw new LinhaFormatadaInvalidaException ( str );
        }

        return casa;
    }

    /**
     * Devolve a morada da casa.
     *
     * @return Morada da casa.
     */
    public
    String getMorada ( ) {
        return morada;
    }

    /**
     * Define a morada da casa.
     *
     * @param morada Morada da casa.
     */
    public
    void setMorada ( String morada ) {
        this.morada = morada;
    }

    /**
     * Devolve o nome do proprietário da casa.
     *
     * @return Nome do proprietário.
     */
    public
    String getProprietario ( ) {
        return this.proprietario;
    }

    /**
     * Define o nome do proprietário.
     *
     * @param proprietario Nome.
     */
    public
    void setProprietario ( String proprietario ) {
        this.proprietario = proprietario;
    }

    /**
     * Devolve o número de identificação fiscal do proprietário.
     *
     * @return NIF do proprietário.
     */
    public
    int getNifProprietario ( ) {
        return this.nif_proprietario;
    }

    /**
     * Define o número de identificação fiscal.
     *
     * @param nif_proprietario Nif do proprietário.
     */
    public
    void setNifProprietario ( int nif_proprietario ) {
        this.nif_proprietario = nif_proprietario;
    }

    /**
     * Devolve uma cópia das divisões da casa.
     *
     * @return Conjunto que contem as divisões da casa.
     */
    public
    Set < Divisao > getDivisoes ( ) {
        Set < Divisao > copia = new HashSet <> ( this.divisoes.size ( ) );
        for (Divisao divisao : this.divisoes.values ( )) {
            copia.add ( divisao.clone ( ) );
        }
        return copia;
    }

    /**
     * Define as divisões da casa.
     *
     * @param divisoes Divisões a copiar.
     */
    public
    void setDivisoes ( Collection < Divisao > divisoes ) {
        if ( divisoes != null ) {
            HashMap < String, Divisao > copia = new HashMap <> ( divisoes.size ( ) );

            for (Divisao divisao : divisoes) {
                copia.put ( divisao.getNome ( ) , divisao.clone ( ) );
            }
            this.divisoes = copia;
        }
    }

    /**
     * Devolve o número de dispositivos na casa.
     *
     * @return Número de dispositivos
     */
    public
    int getNumeroDispositivos ( ) {
        int total = 0;
        for (Divisao divisao : this.divisoes.values ( )) {
            total += divisao.getNumeroDispositivos ( );
        }
        return total;
    }

    /**
     * Devolve o consumo de uma divisão em watts.
     *
     * @return Energia gasta em watts.
     */
    public
    double getConsumoEnergia ( ) {
        double consumo = 0.0;
        for (Divisao divisao : this.divisoes.values ( )) {
            consumo += divisao.getConsumoEnergia ( );
        }
        return consumo;
    }

    /**
     * Devolve o nome do fornecedor de energia.
     *
     * @return Fornecedor de energia.
     */
    public
    String getFornecedor ( ) {
        return this.fornecedor;
    }

    /**
     * Define o fornecedor de energia da casa.
     *
     * @param fornecedor Nome do fornecedor de energia.
     */
    public
    void setFornecedor ( String fornecedor ) {
        this.fornecedor = fornecedor;
    }

    /**
     * Identificador da última fatura emitida.
     *
     * @return Identificador da última fatura.
     */
    public
    long getUltimaFatura ( ) {
        return ultima_fatura;
    }

    /**
     * Guarda o identificador da fatura emitida..
     */
    public
    void setUltimaFatura ( long id_fatura ) {
        this.ultima_fatura = id_fatura;
    }

    /**
     * Devolve o nome da divisão onde se encontra o aparelho.
     *
     * @param id_dispositivo Identificador do aparelho.
     * @return Divisão ou null caso não exista o aparelho.
     */
    public
    String ondeEsta ( String id_dispositivo ) throws DispositivoNaoExisteException {
        boolean found   = false;
        Divisao divisao = null;

        Iterator < Divisao > i = this.divisoes.values ( ).iterator ( );
        while ( i.hasNext ( ) && ! found ) {
            divisao = i.next ( );
            found   = divisao.contem ( id_dispositivo );
        }

        if ( divisao == null ) {
            throw new DispositivoNaoExisteException ( id_dispositivo );
        }

        return divisao.getNome ( );
    }

    /**
     * Altera todos os estados de todos os dispositivos da divisão.
     *
     * @param acao Acao a executar
     */
    public
    void foreachDispositivo ( Consumer < SmartDevice > acao ) {
        for (Divisao divisao : this.divisoes.values ( )) {
            divisao.foreachDispositivo ( acao );
        }
    }

    /**
     * Altera todos os estados de todos os dispositivos da divisão.
     *
     * @param acao Açaõ a executar.
     */
    public
    void foreachDispositivoDivisao ( String divisao , Consumer < SmartDevice > acao ) throws DivisaoNaoExisteException {
        if ( this.divisoes.containsKey ( divisao ) ) {
            this.divisoes.get ( divisao ).foreachDispositivo ( acao );
        }
        else {
            throw new DivisaoNaoExisteException ( divisao );
        }
    }

    /**
     * Remove um dispositivo da divisão da casa.
     *
     * @param id_dispositivo String
     * @param divisao        String
     * @return SmartDevice removido
     * @throws DispositivoNaoExisteException Quando não existe
     * @throws DivisaoNaoExisteException Quando não existe
     */
    public
    SmartDevice removeDispositivo ( String id_dispositivo , String divisao ) throws DispositivoNaoExisteException, DivisaoNaoExisteException {
        SmartDevice dev;
        if ( this.divisoes.containsKey ( divisao ) ) {
            dev = this.divisoes.get ( divisao ).removeDispositivo ( id_dispositivo );
        }
        else {
            throw new DivisaoNaoExisteException ( divisao );
        }
        return dev;
    }

    /**
     * Adiciona um SmartDevice a uma Divisao.
     * @param div Divisao
     * @param device SmartDevice
     * @throws DivisaoNaoExisteException Quando uma Divisao não existe.
     */
    public
    void adicionaDispositivo ( String div , SmartDevice device ) throws DivisaoNaoExisteException {
        if ( this.divisoes.containsKey ( div ) ) {
            this.divisoes.get ( div ).adicionaDispositivo ( device );
        }
        else {
            throw new DivisaoNaoExisteException ( div );
        }
    }

    /**
     * Testa se uma casa é igual a um objeto.
     *
     * @param o Objeto a comparar.
     * @return true - false
     */
    @Override
    public
    boolean equals ( Object o ) {
        if ( this == o ) return true;
        if ( o == null || this.getClass ( ) != o.getClass ( ) ) return false;
        Casa casa = ( Casa ) o;
        return nif_proprietario == casa.nif_proprietario &&
                ultima_fatura == casa.ultima_fatura &&
                Objects.equals ( morada , casa.morada ) &&
                Objects.equals ( proprietario , casa.proprietario ) &&
                Objects.equals ( fornecedor , casa.fornecedor ) &&
                Objects.equals ( divisoes , casa.divisoes );
    }

    /**
     * Devolve uma cópia da casa.
     *
     * @return Cópia da casa.
     */
    @Override
    public
    Casa clone ( ) {
        return new Casa ( this );
    }

    @Override
    public
    String toString ( ) {
        return new StringJoiner ( ", " , Casa.class.getSimpleName ( ) + "[" , "]" )
                .add ( "morada='" + morada + "'" )
                .add ( "proprietario='" + proprietario + "'" )
                .add ( "fornecedor='" + fornecedor + "'" )
                .add ( "nif_proprietario=" + nif_proprietario )
                .add ( "divisoes=" + divisoes )
                .add ( "id_faturas=" + ultima_fatura )
                .toString ( );
    }

    /**
     * Representação textual de uma casa.
     *
     * @return String.
     */


    @Override
    public
    int hashCode ( ) {
        return Objects.hash ( morada );
    }
}
