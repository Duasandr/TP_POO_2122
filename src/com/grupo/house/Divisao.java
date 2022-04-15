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
    public Divisao(){
    }

    /**
     * Construtor por parâmetros.
     * @param nome Nome da divisão.
     * @param aparelhos Aparelhos presentes na divisão.
     */
    public Divisao(String nome , Collection<SmartDevice> aparelhos){
        this.nome = nome;
        this.aparelhos = new HashMap<>();
        this.setAparelhos(aparelhos);
    }

    /**
     * Construtor por cópia.
     * @param divisao Divisão a copiar.
     */
    public Divisao(Divisao divisao){
        this(divisao.nome, divisao.aparelhos.values());
    }

    //Métodos de instância

    //Setters
    /**
     * Define o nome da divisão.
     * @param nome Nome da divisão.
     */
    public void setNome(String nome){
        this.nome = nome;
    }

    /**
     * Define os aparelhos presentes na divisão.
     *
     * @param aparelhos Aparelhos a inserir.
     */
    private void setAparelhos(Collection<SmartDevice> aparelhos){
        if(aparelhos != null) {
            HashMap<String, SmartDevice> clone = new HashMap<>(aparelhos.size());

            this.consumo_energia = 0;
            for (SmartDevice aparelho : aparelhos) {
                clone.put(aparelho.getIdFabricante(), aparelho.clone());
                this.consumo_energia += aparelho.getConsumoEnergia();
            }
            this.aparelhos = clone;
        }
    }

    /**
     * Define o consumo de energia da divisão.
     *
     * @param consumo Consumo a definir.
     */
    private void setConsumoEnergia(double consumo){
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
    public Set<SmartDevice> getAparelhos(){
        Set<SmartDevice> copia = new HashSet<>(this.aparelhos.size());
        for (SmartDevice aparelho : this.aparelhos.values()) {
            copia.add(aparelho.clone());
        }
        return copia;
    }

    /**
     * Devolve o consuma de energia da divisão.
     *
     * @return Consumo de energia.
     */
    public double getConsumoEnergia(){
        return this.consumo_energia;
    }

    public int getNumeroDispositivos(){
        return this.aparelhos.size();
    }

    //Métodos auxiliares

    /**
     * Verifica se um aparelho existe na divisão.
     *
     * @param id Identificador do aparelho.
     * @return true - false
     */
    protected boolean existeAparelho(String id){
        return this.aparelhos.containsKey(id);
    }

    //Métodos de interface

    /**
     * Liga todos os aparelhos presentes na divisão.
     */
    public void ligaTodosDispositivos(){
        this.consumo_energia = 0;
        for (SmartDevice aparelho : this.aparelhos.values()) {
            aparelho.ligar();
            this.consumo_energia += aparelho.getConsumoEnergia();
        }
    }

    /**
     * Desliga todos os aparelhos presentes na divisão.
     */
    public void desligaTodosDispositivos(){
        for (SmartDevice aparelho : this.aparelhos.values()) {
            aparelho.desligar();
        }
        this.consumo_energia = 0;
    }

    /**
     * Liga um aparelho em específico.
     *
     * @param id Identificador do aparelho.
     */
    public void ligaDispositivo(String id){
        if(existeAparelho(id)){
            SmartDevice aparelho = this.aparelhos.get(id);
            if(!aparelho.estaLigado()){
                aparelho.ligar();
                this.consumo_energia += aparelho.getConsumoEnergia();
            }
        }
    }

    /**
     * Desliga um aparelho em específico.
     *
     * @param id Identificador do aparelho.
     */
    public void desligaDispositivo(String id){
        if(existeAparelho(id)){
            SmartDevice aparelho = this.aparelhos.get(id);
            if(aparelho.estaLigado()){
                this.consumo_energia -= aparelho.getConsumoEnergia();
                aparelho.desligar();
            }
        }
    }

    /**
     * Devolve uma cópia da divisão.
     *
     * @return Cópia da divisão.
     */
    @Override
    public Divisao clone(){
        return new Divisao(this);
    }

    /**
     * Representação textual da divisão.
     * @return String.
     */
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
     *
     * @param o Objeto a comparar.
     * @return true - false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Divisao divisao = (Divisao) o;
        return Double.compare(divisao.consumo_energia, consumo_energia) == 0 &&
                Objects.equals(nome, divisao.nome) &&
                Objects.equals(aparelhos, divisao.aparelhos);
    }

    /**
     * Devolve um indíce através de uma função hash.
     *
     * @return Índice.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
