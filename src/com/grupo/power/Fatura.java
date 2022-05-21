package com.grupo.power;

import com.grupo.house.Casa;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.StringJoiner;

public class Fatura implements Serializable {
    //Variáveis de instância

    private long id;
    private String morada;
    private String fornecedor;
    private double total_a_pagar;
    private double        total_consumo;
    private LocalDateTime data;

    //Variáveis de classe

    private static long proximo_id = 0;

    private static void atualizaProximoId(){
        proximo_id = proximo_id + 1;
    }

    //Construtores

    /**
     * Construtor vazio.
     */
    public Fatura(){
        this.id = proximo_id;
    }

    public Fatura( String morada , String fornecedor, double total_a_pagar , double total_consumo, LocalDateTime data , LocalDateTime fim){
        this();
        this.morada        = morada;
        this.fornecedor    = fornecedor;
        this.total_a_pagar = total_a_pagar * data.until ( fim, ChronoUnit.DAYS );
        this.total_consumo = total_consumo * data.until ( fim, ChronoUnit.DAYS );
        this.data          = fim;
        atualizaProximoId();
    }

    /**
     * Construtor por parâmetors.
     * @param casa
     * @param data
     * @param fim
     */
    public Fatura( Casa casa , FornecedorEnergia fornecedor , LocalDateTime data , LocalDateTime fim){
        this( casa.getMorada(), fornecedor.getNome(), fornecedor.calculaValorPagar(casa), casa.getConsumoEnergia(), data , fim);
        casa.setUltimaFatura ( this.id);
        fornecedor.atualizaFaturacao(this.total_a_pagar);
    }

    /**
     * Construtor por cópia.
     * @param fatura Fatura a copiar.
     */
    public Fatura(Fatura fatura){
        this.id = fatura.id;
        this.morada = fatura.morada;
        this.fornecedor = fatura.fornecedor;
        this.total_a_pagar = fatura.total_a_pagar;
        this.total_consumo = fatura.total_consumo;
        this.data          = fatura.data;
    }

    public long getId() {
        return this.id;
    }

    public String getFornecedor() {
        return this.fornecedor;
    }
    public String getMorada() {
        return this.morada;
    }

    public double getTotalAPagar() {
        return this.total_a_pagar;
    }

    public double getTotalConsumo() {
        return this.total_consumo;
    }

    public LocalDateTime getData () {
        return this.data;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public
    boolean equals ( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass ( ) != o.getClass ( ) ) return false;
        Fatura fatura = ( Fatura ) o;
        return id == fatura.id &&
                Double.compare ( fatura.total_a_pagar , total_a_pagar ) == 0 &&
                Double.compare ( fatura.total_consumo , total_consumo ) == 0 &&
                Objects.equals ( morada , fatura.morada ) &&
                Objects.equals ( fornecedor , fatura.fornecedor ) &&
                Objects.equals ( data , fatura.data );
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     *
     * @return
     */
    @Override
    public Fatura clone(){
        return new Fatura(this);
    }

    /**
     *
     * @return
     */
    @Override
    public
    String toString ( ) {
        return new StringJoiner ( ", " , Fatura.class.getSimpleName ( ) + "[" , "]" )
                .add ( "id=" + id )
                .add ( "morada='" + morada + "'" )
                .add ( "fornecedor='" + fornecedor + "'" )
                .add ( "total_a_pagar=" + total_a_pagar )
                .add ( "total_consumo=" + total_consumo )
                .add ( "data=" + data )
                .toString ( );
    }
}
