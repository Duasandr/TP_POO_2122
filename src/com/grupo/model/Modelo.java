package com.grupo.model;

import com.grupo.comparators.DoubleDecrescente;
import com.grupo.device.SmartDevice;
import com.grupo.exceptions.*;
import com.grupo.house.Casa;
import com.grupo.house.Divisao;
import com.grupo.power.Fatura;
import com.grupo.power.FornecedorEnergia;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

public
class Modelo implements Serializable {
    // Variáveis de instância
    private Map < String, FornecedorEnergia > fornecedores;
    private       Map < String, Casa > casas;
    private final Map < Long, Fatura > faturas;
    private       long                 dias;

    //Construtores

    /**
     * Construtor vazio.
     */
    public
    Modelo ( ) {
        this.fornecedores = new HashMap <> ( );
        this.casas        = new HashMap <> ( );
        this.faturas      = new HashMap <> ( );
    }

    /**
     * Construtor por parâmetros.
     *
     * @param fornecedores
     * @param casas
     */
    public
    Modelo ( Collection < FornecedorEnergia > fornecedores , Collection < Casa > casas ) {
        this ( );
        this.setFornecedores ( fornecedores );
        this.setCasas ( casas );
    }

    //Getters

    /**
     * Devolve um iterador para os fornecedores contidos.
     *
     * @return Iterator
     */
    public
    Iterator < FornecedorEnergia > getFornecedor ( ) {
        return this.fornecedores.values ( ).iterator ( );
    }

    /**
     * Devolve o fornecedor da casa fornecida.
     *
     * @param casa Casa
     * @return FornecedorEnergia
     */
    public
    FornecedorEnergia getFornecedor ( Casa casa ) {
        return this.fornecedores.get ( casa.getFornecedor ( ) ).clone ( );
    }

    /**
     * Devolve um iterador para uma lista ordenada por um comparador.
     *
     * @param comp Comparator
     * @return Iterator
     */
    public
    Iterator < FornecedorEnergia > getFornecedor ( Comparator < FornecedorEnergia > comp ) {
        List < FornecedorEnergia > fornecedor = this.fornecedores.values ( )
                .stream ( )
                .map ( FornecedorEnergia::clone )
                .toList ( );
        fornecedor.sort ( comp );
        return fornecedor.iterator ( );
    }

    /**
     * Devolve uma casa.
     *
     * @param morada String
     * @return Casa
     */
    public
    Casa getCasa ( String morada ) {
        return this.casas.get ( morada ).clone ( );
    }

    /**
     * Devolve um iterador para as casas contidas.
     *
     * @return Iterator
     */
    public
    Iterator < Casa > getCasa ( ) {
        return this.casas.values ( )
                .stream ( )
                .map ( Casa::clone )
                .toList ( ).iterator ( );
    }

    /**
     * Devolve um iterador para uma lista de casas ordenada por ordem decrescente de consumo entre as datas indicadas.
     *
     * @param inicio LocalDatetime
     * @param fim    LocalDateTime
     * @return Iterator
     */
    public
    Map < Double , Set<String> > getFatura ( LocalDateTime inicio , LocalDateTime fim ) {
        Map<Double , Set<String>> r = new TreeMap <> ( new DoubleDecrescente () );

        for (String s : casas.keySet ( )) {
            double c = getConsumo ( s,inicio,fim );
            if(r.containsKey ( c )){
                r.get ( c ).add ( s );
            }else{
                Set<String> set = new HashSet<> ();
                set.add ( s );
                r.put ( c , set);
            }
        }

        return r;
    }

    /**
     * Devolve uma fatura.
     *
     * @param id_fatura long
     * @return Fatura
     */
    public
    Fatura getFatura ( long id_fatura ) {
        return this.faturas.get ( id_fatura ).clone ( );
    }

    /**
     * Devolve um iterador para as faturas de um fornecedor.
     *
     * @return Iterator
     */
    public
    Iterator < Fatura > getFatura ( String nome_fornecedor ) {
        return this.faturas.values ( )
                .stream ( )
                .filter ( fatura -> fatura.getFornecedor ( ).equals ( nome_fornecedor ) )
                .map ( Fatura::clone )
                .toList ( )
                .iterator ( );
    }

    public
    Iterator < Fatura > getFatura ( ) {
        return this.faturas.values ( ).iterator ( );
    }

    public Double getConsumo(String casa , LocalDateTime inicio , LocalDateTime fim){
        if(this.casas.containsKey ( casa )){
            return faturas.values ().stream ().
                    filter ( fatura ->  fatura.getMorada ().equals ( casa ) &&
                            fatura.getData ().isAfter ( inicio ) &&
                            fatura.getData ().isBefore ( fim ) ).
                    map ( Fatura::getTotalConsumo ).
                    toList ().
                    stream( ).
                    reduce ( 0.0 , Double::sum );
        }
        return 0.0;
    }

    public
    void emiteFaturas ( LocalDateTime inicio , LocalDateTime fim ) {
        for (Casa casa : this.casas.values ( )) {
            FornecedorEnergia fornecedor = this.fornecedores.get ( casa.getFornecedor ( ) );
            Fatura            fatura     = new Fatura ( casa , fornecedor , inicio , fim );
            this.faturas.put ( fatura.getId ( ) , fatura );
        }
    }

    public
    void setFornecedores ( Collection < FornecedorEnergia > fornecedores ) {
        HashMap < String, FornecedorEnergia > copia = new HashMap <> ( fornecedores.size ( ) );
        for (FornecedorEnergia fornecedor : fornecedores) {
            copia.put ( fornecedor.getNome ( ) , fornecedor.clone ( ) );
        }
        this.fornecedores = copia;
    }

    //Setters

    public
    void setCasas ( Collection < Casa > casas ) {
        HashMap < String, Casa > copia = new HashMap <> ( casas.size ( ) );
        for (Casa casa : casas) {
            copia.put ( casa.getMorada ( ) , casa.clone ( ) );
        }
        this.casas = copia;
    }

    public
    void atualizaDias ( long dias ) {
        this.dias += dias;
    }

    public
    long getDias ( ) {
        return this.dias;
    }

    public
    void setDias ( long dias ) {
        this.dias = dias;
    }

    /**
     * Faz o parse de uma lista de String de FornecedorEnergia
     *
     * @param list List
     */
    public
    void setFornecedores ( List < String > list ) {
        for (String str : list) {
            FornecedorEnergia fornecedor = FornecedorEnergia.parse ( str );
            this.fornecedores.put ( fornecedor.getNome ( ) , fornecedor );
        }
    }


    /**
     * Faz o parse de uma lista de String de Casa
     *
     * @param list List
     */
    public
    void setCasas ( List < String > list ) throws LinhaFormatadaInvalidaException, SmartDeviceInvalidoException, TonalidadeInvalidaException, EstadoInvalidoException {
        for (String str : list) {
            Casa casa = Casa.parse ( str );
            this.casas.put ( casa.getMorada ( ) , casa );
        }
    }

    //Métodos de instância

    /**
     * Altera o estado de todos os aparelhos de todas as casas.
     *
     * @param acao Ação a executar.
     */
    public
    void foreachDispositivo ( Consumer < SmartDevice > acao ) {
        for (Casa casa : this.casas.values ( )) {
            casa.foreachDispositivo ( acao );
        }
    }

    /**
     * Altera o estado de todos os dispositivos de uma casa.
     *
     * @param morada Morada da casa.
     * @param acao   Acao a executar.
     */
    public
    void foreachDispositivo ( String morada , Consumer < SmartDevice > acao ) throws CasaInexistenteException {
        if ( this.casas.containsKey ( morada ) ) {
            this.casas.get ( morada ).foreachDispositivo ( acao );
        }
        else {
            throw new CasaInexistenteException ( );
        }
    }

    /**
     * Altera o estado de todos os dispositivos de uma divisão.
     *
     * @param morada  Morada da casa.
     * @param divisao Nome da divisão.
     * @param acao    Ação a executar.
     * @throws CasaInexistenteException  Quando não existe a casa.
     * @throws DivisaoNaoExisteException Quando não existe a divisão.
     */
    public
    void foreachDispositivo ( String morada , String divisao , Consumer < SmartDevice > acao ) throws CasaInexistenteException, DivisaoNaoExisteException {
        if ( this.casas.containsKey ( morada ) ) {
            this.casas.get ( morada ).foreachDispositivo ( divisao , acao );
        }
        else {
            throw new CasaInexistenteException ( );
        }
    }

    public
    AbstractMap.SimpleEntry < String, SmartDevice > removeDispositivo ( String morada , String id_dispositivo ) throws DispositivoNaoExisteException, DivisaoNaoExisteException, CasaInexistenteException {
        SmartDevice dev;
        String      local;
        Casa        casa;
        if ( this.casas.containsKey ( morada ) ) {
            casa  = this.casas.get ( morada );
            local = casa.ondeEsta ( id_dispositivo );
            dev   = casa.remove ( id_dispositivo , local );
        }
        else {
            throw new CasaInexistenteException ( );
        }
        return new AbstractMap.SimpleEntry < String, SmartDevice > ( local , dev );
    }

    public
    void adiciona ( String casa , String divisao , SmartDevice device ) throws DivisaoNaoExisteException, CasaInexistenteException {
        if ( this.casas.containsKey ( casa ) ) {
            this.casas.get ( casa ).adiciona ( divisao , device );
        } else {
            throw new CasaInexistenteException ();
        }
    }

    public
    void adiciona ( FornecedorEnergia fornecedor )  {
        this.fornecedores.put ( fornecedor.getNome () , fornecedor.clone ());
    }

    public
    void adiciona ( String casa , Divisao divisao ) throws CasaInexistenteException {
        if ( this.casas.containsKey ( casa ) ) {
            this.casas.get ( casa ).adiciona ( divisao );
        }else {
            throw new CasaInexistenteException ();
        }
    }

    public
    void adiciona ( Casa casa) {
        this.casas.put ( casa.getMorada () , casa.clone () );
    }

    public
    void verificaContrato ( ) {
        for (Casa casa : this.casas.values ( )) {
            if ( ! this.fornecedores.containsKey ( casa.getFornecedor ( ) ) ) {
                this.casas.remove ( casa.getMorada ( ) );
            }
        }
    }

    @Override
    public
    String toString ( ) {
        final StringBuilder sb = new StringBuilder ( "Modelo{" );
        sb.append ( "fornecedores=" ).append ( fornecedores );
        sb.append ( ", casas=" ).append ( casas );
        sb.append ( ", faturas=" ).append ( faturas );
        sb.append ( '}' );
        return sb.toString ( );
    }
}
