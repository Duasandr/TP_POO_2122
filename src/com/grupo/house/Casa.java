package com.grupo.house;

import java.util.*;


public class Casa implements CasaInteligente{
    //Variáveis de instância

    private String proprietario;
    private int nif_proprietario;
    private HashMap<String , Divisao> divisoes;

    //Construtores

    /**
     * Construtor vazio.
     */
    public Casa(){
        this("null",0,null);
    }

    /**
     * Construtor por parâmetros
     * @param proprietario Nome do proprietário.
     * @param nif_proprietario Número de identificação fiscal do proprietário.
     * @param divisoes Divisões da casa.
     */
    public Casa(String proprietario, int nif_proprietario , Set<Divisao> divisoes){
        this.setProprietario(proprietario);
        this.setNifProprietario(nif_proprietario);
        this.setDivisoes(divisoes);
    }

    public Casa(Casa casa){
        this(casa.getProprietario(),casa.getNifProprietario(), (Set<Divisao>) casa.divisoes.values());
    }

    //Métodos de instância

    //Setters

    /**
     * Define o nome do proprietário.
     * @param proprietario Nome.
     */
    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    /**
     * Define o número de identificação fiscal.
     * @param nif_proprietario Nif do proprietário.
     */
    public void setNifProprietario(int nif_proprietario) {
        this.nif_proprietario = nif_proprietario;
    }

    /**
     * Define as divisões da casa.
     * @param divisoes Divisões a copiar.
     */
    private void setDivisoes(Set<Divisao> divisoes) {
        if(divisoes != null) {
            HashMap<String, Divisao> copia = new HashMap<>(divisoes.size());
            for (Divisao divisao : divisoes) {
                copia.put(divisao.getNome(), divisao.clone());
            }
            this.divisoes = copia;
        }
    }

    //Getters

    /**
     * Devolve o nome do proprietário da casa.
     *
     * @return Nome do proprietário.
     */
    public String getProprietario(){
        return this.proprietario;
    }

    /**
     * Devolve o número de identificação fiscal do proprietário.
     *
     * @return NIF do proprietário.
     */
    public int getNifProprietario() {
        return this.nif_proprietario;
    }

    /**
     * Devolve uma cópia das divisões da casa.
     *
     * @return Conjunto que contem as divisões da casa.
     */
    private Set<Divisao> getDivisoes(){
        Set<Divisao> copia = new HashSet<>(this.divisoes.size());
        for (Divisao divisao : this.divisoes.values()) {
            copia.add(divisao.clone());
        }
        return copia;
    }

    //Métodos auxiliares
    /**
     * testa se uma divisão existe na casa.
     *
     * @param divisao Nome da divisão.
     * @return true - false
     */
    private boolean existeDivisao(String divisao){
        return this.divisoes.containsKey(divisao);
    }

    /**
     * Devolve o nome da divisão onde se encontra o aparelho.
     *
     * @param id Identificador do aparelho.
     * @return Nome da divisão ou null caso não exista o aparelho.
     */
    private String divisaoDoAparelho(String id){
        boolean found = false;
        Divisao divisao = null;

        Iterator<Divisao> i = this.divisoes.values().iterator();
        while(i.hasNext() && !found){
            divisao = i.next();
            found = divisao.existeAparelho(id);
        }
        return found ? divisao.getNome() : null;
    }

    //Métodos de interface
    /**
     * Liga todos os dispositivos de uma divisão da casa.
     *
     * @param nome Nome da divisão.
     */
    @Override
    public void ligarTodosDispositivos(String nome) {
        if(existeDivisao(nome)){
            this.divisoes.get(nome).ligaTodosDispositivos();
        }
    }

    /**
     * Desliga todos os dispositivos de uma divisão da casa.
     *
     * @param nome Nome da divisão.
     */
    @Override
    public void desligarTodosDispositivos(String nome) {
        if(existeDivisao(nome)){
            this.divisoes.get(nome).desligaTodosDispositivos();
        }
    }

    /**
     * Liga um aparelho em específico.
     * @param id Identificador do aparelho.
     */
    @Override
    public void ligarDispositivo(String id) {
        String divisao = divisaoDoAparelho(id);
        if(divisao != null){
            this.divisoes.get(divisao).ligaDispositivo(id);
        }
    }

    /**
     * Desliga um aparelho em específico.
     * @param id Identificador do aparelho.
     */
    @Override
    public void desligarDispositivo(String id) {
        String divisao = divisaoDoAparelho(id);
        if(divisao != null){
            this.divisoes.get(divisao).desligaDispositivo(id);
        }
    }

    //Métodos de Object

    /**
     * Testa se uma casa é igual a um objeto.
     * @param o Objeto a comparar.
     * @return true - false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Casa casa = (Casa) o;
        return this.nif_proprietario == casa.nif_proprietario &&
                this.getProprietario().equals(casa.getProprietario()) &&
                this.divisoes.equals(casa.divisoes);
    }

    /**
     * Devolve uma cópia da casa.
     * @return Cópia da casa.
     */
    @Override
    public Casa clone(){
        return new Casa(this);
    }

    /**
     * Representação textual de uma casa.
     * @return String.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Casa{");
        sb.append("proprietario='").append(proprietario).append('\'');
        sb.append(", nif_proprietario=").append(nif_proprietario);
        sb.append(", divisoes=").append(divisoes);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(proprietario, nif_proprietario);
    }
}
