package com.grupo.power;

import com.grupo.comparators.FaturaPorId;
import com.grupo.house.Casa;

import java.util.*;

public class FornecedorEnergia {
    //Variáveis de instância

    private String nome;
    private double valor_base;
    private double imposto;
    private FuncaoConsumo funcao_consumo;
    private Set<Fatura> faturas;
    private double faturacao;

    //Variáveis de classe

    private static final double valor_base_por_omissao = 0.13;
    private static final double imposto_por_omissao = 0.06;

    //Construtores

    /**
     * Construtor vazio.
     */
    public FornecedorEnergia(){
        this.faturas = new TreeSet<>(new FaturaPorId());
    }

    /**
     * Construtor por parâmetros.
     * @param nome Nome do fornecedor
     * @param valor_base Preço por kw
     * @param imposto Imposto
     * @param funcao_consumo Função que calcula o consumo
     * @param faturas Faturas
     */
    public FornecedorEnergia(String nome , double valor_base , double imposto , FuncaoConsumo funcao_consumo,Collection<Fatura> faturas){
        this.nome = nome;
        this.valor_base = valor_base;
        this.imposto = imposto;
        this.funcao_consumo = funcao_consumo;
        this.setFaturas(faturas);
    }

    /**
     * Construtor por parâmetros.
     * @param nome Nome do fornecedor.
     * @param valor_base Valor base por kilowat
     * @param imposto Imposto
     * @param funcao_consumo Função que calcula o valor total a pagar.
     */
    public FornecedorEnergia(String nome , double valor_base , double imposto , FuncaoConsumo funcao_consumo){
        this();
        this.nome = nome;
        this.valor_base = valor_base;
        this.imposto = imposto;
        this.funcao_consumo = funcao_consumo;
    }

    /**
     * Construtor por cópia.
     * @param fornecedor Cópia
     */
    public FornecedorEnergia(FornecedorEnergia fornecedor){
        this(fornecedor.nome, fornecedor.valor_base, fornecedor.imposto,fornecedor.funcao_consumo,fornecedor.faturas);
    }

    //Getters

    /**
     * Devolve o nome do fornecedor.
     * @return String Nome
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Devolve o preço base por kw.
     * @return double Valor base.
     */
    public double getValorBase() {
        return this.valor_base;
    }

    /**
     * Devolve o imposto.
     * @return double Imposto
     */
    public double getImposto() {
        return imposto;
    }

    /**
     * Devolve o conjunto de faturas.
     * @return Faturas
     */
    public Set<Fatura> getFaturas() {
        Set<Fatura> copia = new TreeSet<>(new FaturaPorId());
        for (Fatura fatura : this.faturas) {
            copia.add(fatura.clone());
        }
        return copia;
    }

    /**
     * Devolve o total faturado.
     * @return double Faturação.
     */
    public double getFaturacao(){
        return this.faturacao;
    }

    //Setters

    /**
     * Define o nome do fornecedor.
     * @param nome String Nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Define o valor base por kw.
     * @param valor_base Preço kw
     */
    public void setValorBase(double valor_base) {
        this.valor_base = valor_base;
    }

    /**
     * Define o imposto.
     * @param imposto double Imposto
     */
    public void setImposto(double imposto) {
        this.imposto = imposto;
    }

    /**
     * Define a função de consumo.
     * @param funcao_consumo Função consumo a aplicar.
     */
    public void setFuncaoConsumo(FuncaoConsumo funcao_consumo) {
        this.funcao_consumo = funcao_consumo;
    }

    /**
     * Define as faturas do fornecedor.
     * @param faturas Faturas a utilizar
     */
    public void setFaturas(Collection<Fatura> faturas){
        this.faturacao = 0;
        this.faturas = new TreeSet<>(new FaturaPorId());
        for (Fatura fatura : faturas) {
            this.guardaFatura(fatura);
        }
    }

    /**
     * Define a atual faturação do fornecedor
     * @param faturacao Faturação total
     */
    public void setFaturacao(double faturacao){
        this.faturacao = faturacao;
    }

    //Métodos de instância

    /**
     * Devolve o total consumo de energia de uma casa a pagar.
     * @param casa Casa
     * @return double Total a pagar.
     */
    public double calculaValorPagar(Casa casa){
        return this.funcao_consumo.calculaTotalPagar(casa,this);
    }

    /**
     * Guarda uma fatura emitida e atualiza o valor da faturação.
     * @param fatura Fatura
     */
    public void guardaFatura(Fatura fatura){
        this.faturacao += fatura.getTotalConsumo();
        this.faturas.add(fatura.clone());
    }

    /**
     * Transforma uma string num objeto do tipo FOrnecedorEnergia passando como parâmetros valores por omissão.
     * @param str Nome do fornecedor
     * @return FornecedorEnergia Uma nova instância de Fornecedor
     */
    public FornecedorEnergia parse(String str){
        String[] tokens = str.split(";");
        return new FornecedorEnergia(str,valor_base_por_omissao,imposto_por_omissao,new FuncaoConsumoPadrao());
    }

    //Métodos herdados de Object

    /**
     * Compara a instância com um objeto.
     * @param o Objeto a comparar
     * @return true - false
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
     * Cria uma cópia da instância.
     * @return Cópia
     */
    @Override
    public FornecedorEnergia clone(){
        return new FornecedorEnergia(this);
    }

    /**
     * Representação textual do fornecedor.
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FornecedorEnergia{");
        sb.append("nome='").append(nome).append('\'');
        sb.append(", valor_base=").append(valor_base);
        sb.append(", imposto=").append(imposto);
        sb.append(", funcao_consumo=").append(funcao_consumo.toString());
        sb.append(", faturas=").append(faturas);
        sb.append(", faturacao=").append(faturacao);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, valor_base, imposto, funcao_consumo, faturas, faturacao);
    }
}
