package com.grupo.power;

import java.util.*;

public class FornecedorEnergia {
    //Variáveis de instância

    private String nome;
    private double valor_base;
    private double imposto;
    private FuncaoDesconto funcao_desconto;
    private TreeSet<Long> faturas;
    private double faturacao;

    //Construtores

    /**
     * Construtor vazio.
     */
    public FornecedorEnergia(){
    }

    /**
     * Contrutor por parâmetros.
     * @param nome Nome do fornecedor.
     * @param valor_base Valor base por kilowat
     * @param imposto Imposto
     * @param funcao_desconto Função que calcula o desconto a efetiar.
     */
    public FornecedorEnergia(String nome , double valor_base , double imposto , FuncaoDesconto funcao_desconto){
        this.nome = nome;
        this.valor_base = valor_base;
        this.imposto = imposto;
        this.funcao_desconto = funcao_desconto;
        this.faturas = new TreeSet<>();
    }

    /**
     * Construtor por cópia.
     * @param fornecedor
     */
    public FornecedorEnergia(FornecedorEnergia fornecedor){
        this(fornecedor.nome, fornecedor.valor_base, fornecedor.imposto,fornecedor.funcao_desconto);
    }

    //Getters

    /**
     *
     * @return
     */
    public String getNome() {
        return this.nome;
    }

    /**
     *
     * @return
     */
    public double getValorBase() {
        return this.valor_base;
    }

    /**
     *
     * @return
     */
    public double getImposto() {
        return imposto;
    }

    public Long[] getFaturas() {
        return this.faturas.toArray(Long[]::new);
    }

    public double getFaturacao(){
        return this.faturacao;
    }


    //Setters

    /**
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *
     * @param valor_base
     */
    public void setValorBase(double valor_base) {
        this.valor_base = valor_base;
    }

    /**
     *
     * @param imposto
     */
    public void setImposto(double imposto) {
        this.imposto = imposto;
    }

    /**
     *
     * @param funcao_desconto
     */
    public void setFuncaoDesconto(FuncaoDesconto funcao_desconto) {
        this.funcao_desconto = funcao_desconto;
    }

    //Métodos de instância

    public double calculaDesconto(double consumo , int aparelhos){
        return funcao_desconto.descontoPorDia(consumo,aparelhos);
    }

    public void guardaFatura(long id , double valor){
        this.faturacao += valor;
        this.faturas.add(id);
    }

    //Métodos Object

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FornecedorEnergia that = (FornecedorEnergia) o;
        return Double.compare(that.valor_base, valor_base) == 0 &&
                Double.compare(that.imposto, imposto) == 0 &&
                Objects.equals(nome, that.nome);
    }

    /**
     *
     * @return
     */
    @Override
    public FornecedorEnergia clone(){
        return new FornecedorEnergia(this);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FornecedorEnergia{");
        sb.append("nome='").append(nome).append('\'');
        sb.append(", valor_base=").append(valor_base);
        sb.append(", imposto=").append(imposto);
        sb.append(", funcao_desconto=").append(funcao_desconto);
        sb.append('}');
        return sb.toString();
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
