package com.grupo.power;

import com.grupo.house.Casa;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Objects;

public class Fatura implements Serializable {
    //Variáveis de instância

    private long id;
    private String morada;
    private String fornecedor;
    private double total_a_pagar;
    private double total_consumo;
    private LocalDateTime inicio;
    private LocalDateTime fim;

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

    public Fatura(String morada , String fornecedor, double total_a_pagar , double total_consumo,LocalDateTime inicio,LocalDateTime fim){
        this();
        this.morada = morada;
        this.fornecedor = fornecedor;
        this.total_a_pagar = total_a_pagar;
        this.total_consumo = total_consumo;
        this.inicio = inicio;
        this.fim = fim;
        atualizaProximoId();
    }

    /**
     * Construtor por parâmetors.
     * @param casa
     * @param inicio
     * @param fim
     */
    public Fatura(Casa casa , FornecedorEnergia fornecedor ,LocalDateTime inicio , LocalDateTime fim){
        this(casa.getMorada(), fornecedor.getNome(), fornecedor.calculaValorPagar(casa), casa.getConsumoEnergia(), inicio,fim);
        casa.guardaFatura(this.id);
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
        this.inicio = fatura.inicio;
        this.fim = fatura.fim;
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

    public LocalDateTime getInicio() {
        return this.inicio;
    }

    public LocalDateTime getFim() {
        return this.fim;
    }

    public static Fatura emiteFatura(Casa casa,FornecedorEnergia fornecedor,LocalDateTime inicio , LocalDateTime fim){
        Fatura fatura = new Fatura(casa,fornecedor,inicio,fim);

        return fatura;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fatura fatura = (Fatura) o;
        return this.id == fatura.id &&
                Double.compare(fatura.total_a_pagar, total_a_pagar) == 0 &&
                Objects.equals(morada, fatura.morada) &&
                Objects.equals(fornecedor, fatura.fornecedor);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, morada, fornecedor, total_a_pagar);
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
    public String toString() {
        final StringBuilder sb = new StringBuilder("Fatura{");
        sb.append("id=").append(id);
        sb.append(", morada='").append(morada).append('\'');
        sb.append(", fornecedor='").append(fornecedor).append('\'');
        sb.append(", total_a_pagar=").append(total_a_pagar);
        sb.append(", inicio=").append(inicio);
        sb.append(", fim=").append(fim);
        sb.append('}');
        return sb.toString();
    }
}
