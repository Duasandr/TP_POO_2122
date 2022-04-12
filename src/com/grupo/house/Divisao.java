package com.grupo.house;

import com.grupo.device.SmartDevice;

import java.util.*;

public class Divisao {
    //Variáveis de instância
    private String nome;
    private HashMap<String,SmartDevice> aparelhos;
    private double consumo_energia;

    //Construtores

    /**
     * Construtor vazio
     */
    protected Divisao(){
        this("null",null);
    }

    /**
     * Construtor por parâmetros.
     * @param nome Nome da divisão.
     * @param aparelhos Aparelhos presentes na divisão.
     */
    protected Divisao(String nome , Collection<SmartDevice> aparelhos){
        this.setNome(nome);
        this.setAparelhos(aparelhos);
        this.atualizaConsumoEnergia();

    }

    /**
     * Construtor por cópia.
     * @param divisao Divisão a copiar.
     */
    protected Divisao(Divisao divisao){
        this.setNome(divisao.getNome());
        this.setAparelhos(divisao.aparelhos.values());
        this.setConsumoEnergia(divisao.getConsumoEnergia());
    }

    //Métodos de instância

    //Setters

    /**
     * Define o nome da divisão.
     * @param nome Nome da divisão.
     */
    protected void setNome(String nome){
        this.nome = nome;
    }

    /**
     * Define os aparelhos presentes na divisão.
     * @param aparelhos Aparelhos a inserir.
     */
    protected void setAparelhos(Collection<SmartDevice> aparelhos){
        if(aparelhos != null) {
            HashMap<String, SmartDevice> clone = new HashMap<String, SmartDevice>(aparelhos.size());
            for (SmartDevice aparelho : aparelhos) {
                clone.put(aparelho.getIdFabricante(), aparelho.clone());
            }
            this.aparelhos = clone;
            this.atualizaConsumoEnergia();
        }
    }

    /**
     * Define o consumo de energia da divisão.
     * @param consumo Consumo a definir.
     */
    protected void setConsumoEnergia(double consumo){
        this.consumo_energia = consumo;
    }

    //Getters

    /**
     * Devolve o nome da divisão.
     * @return Nome da divisão.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Devolve um conjunto com os aparelhos da divisão.
     * @return Conjunto de aparelhos.
     */
    protected Collection<SmartDevice> getAparelhos(){
        HashMap<String,SmartDevice> original = this.aparelhos;
        HashSet<SmartDevice> clone = new HashSet<SmartDevice>(original.size());
        for (SmartDevice aparelho : original.values()) {
            clone.add(aparelho.clone());
        }
        return clone;
    }

    /**
     * Devolve o consuma de energia da divisão.
     * @return Consumo de energia.
     */
    protected double getConsumoEnergia(){
        return this.consumo_energia;
    }

    //Métodos

    /**
     * Atualiza o consumo de energia da divisão.
     */
    protected void atualizaConsumoEnergia() {
        double consumo = 0.0;
        if (this.aparelhos != null) {
            for (SmartDevice aparelho : this.aparelhos.values()) {
                consumo += aparelho.consumoEnergia();
            }
        }
        this.consumo_energia = consumo;
    }

    /**
     * Verifica se um aparelho existe na divisão.
     * @param id Identificador do aparelho.
     * @return true - false
     */
    protected boolean existeAparelho(String id){
        return this.aparelhos.containsKey(id);
    }

    /**
     * Altera o estado de todos os aparelhos presentes na divisão.
     * @param novo_estado Novo estado.
     */
    protected void alteraEstados(boolean novo_estado){
        for (SmartDevice aparelho : this.aparelhos.values()) {
            aparelho.setEstado(novo_estado);
        }
    }

    /**
     * Altera o estado de um aparelho em especifico.
     * @param id Identificador do aparelho.
     * @param novo_estado Novo estado.
     */
    protected void alteraUnicoEstado(String id , boolean novo_estado){
        if(existeAparelho(id)){
            this.aparelhos.get(id).setEstado(novo_estado);
        }
    }

    /**
     * Devolve uma cópia da divisão.
     * @return Cópia da divisão.
     */
    @Override
    public Divisao clone(){
        return new Divisao(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Divisao{");
        sb.append("nome='").append(nome).append('\'');
        sb.append(", aparelhos=").append(aparelhos);
        sb.append(", consumo_energia=").append(consumo_energia);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Testa se uma divisão é igual a um objeto.
     * @param o Objeto a comparar.
     * @return true - false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Divisao divisao = (Divisao) o;
        return Double.compare(divisao.consumo_energia, consumo_energia) == 0 &&
                this.aparelhos.equals(divisao.aparelhos) &&
                this.nome.equals(divisao.nome);
    }

    /**
     * Devolve um indíce através de uma função hash.
     * @return Índice.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
