package com.grupo.house;

import com.grupo.device.SmartDevice;

import java.util.*;


public class Casa {
    //Variáveis de instância

    private String morada;
    private String proprietario;
    private String fornecedor;
    private int nif_proprietario;
    private HashMap<String , Divisao> divisoes;
    private int numero_dispositivos;
    private double consumo_energia;
    private TreeSet<Long> faturas;

    //Construtores

    /**
     * Construtor vazio.
     */
    public Casa(){
    }

    /**
     * Construtor por parâmetros
     * @param proprietario Nome do proprietário.
     * @param nif_proprietario Número de identificação fiscal do proprietário.
     * @param divisoes Divisões da casa.
     */
    public Casa(String proprietario,String morada , int nif_proprietario , Collection<Divisao> divisoes , String fornecedor){
        this.proprietario = proprietario;
        this.nif_proprietario = nif_proprietario;
        this.fornecedor = fornecedor;
        this.morada = morada;
        this.divisoes = new HashMap<>();
        this.faturas = new TreeSet<>();
        this.setDivisoes(divisoes);
    }

    public Casa(Casa casa){
        this(casa.proprietario , casa.morada, casa.nif_proprietario, casa.divisoes.values(), casa.fornecedor);
    }

    //Métodos de instância

    //Setters

    /**
     * Define a morada da casa.
     * @param morada Morada da casa.
     */
    public void setMorada(String morada) {
        this.morada = morada;
    }

    /**
     * Define o nome do proprietário.
     * @param proprietario Nome.
     */
    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    /**
     * Define o fornecedor de energia da casa.
     * @param fornecedor Nome do fornecedor de energia.
     */
    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
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
    public void setDivisoes(Collection<Divisao> divisoes) {
        if(divisoes != null) {
            HashMap<String, Divisao> copia = new HashMap<>(divisoes.size());

            this.numero_dispositivos = 0;
            this.consumo_energia = 0;
            for (Divisao divisao : divisoes) {
                copia.put(divisao.getNome(), divisao.clone());
                numero_dispositivos += divisao.getNumeroDispositivos();
                consumo_energia += divisao.getConsumoEnergia();
            }
            this.divisoes = copia;
        }
    }

    /**
     * Define o número de dispositivos da casa.
     * @param numero_dispositivos Número de dispositivos.
     */
    public void setNumeroDispositivos(int numero_dispositivos) {
        this.numero_dispositivos = numero_dispositivos;
    }

    /**
     * Define o consumo de energia da casa.
     * @param consumo_energia consumo de energia em watts.
     */
    public void setConsumoEnergia(double consumo_energia) {
        this.consumo_energia = consumo_energia;
    }

    //Getters

    /**
     * Devolve a morada da casa.
     * @return Morada da casa.
     */
    public String getMorada() {
        return morada;
    }

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
    public Set<Divisao> getDivisoes(){
        Set<Divisao> copia = new HashSet<>(this.divisoes.size());
        for (Divisao divisao : this.divisoes.values()) {
            copia.add(divisao.clone());
        }
        return copia;
    }

    /**
     * Devolve o número de dispositivos na casa.
     * @return Número de dispositivos
     */
    public int getNumeroDispositivos(){
        return this.numero_dispositivos;
    }

    /**
     * Devolve o consumo de uma divisão em watts.
     * @return Energia gasta em watts.
     */
    public double getConsumoEnergia(){
        return this.consumo_energia;
    }

    /**
     * Devolve o nome do fornecedor de energia.
     * @return Fornecedor de energia.
     */
    public String getFornecedor() {
        return this.fornecedor;
    }

    /**
     * Devolve um array com os identificadores de faturas emitidas
     * @return Array de faturas emitidas.
     */
    public Long[] getFaturas(){
        return this.faturas.toArray(Long[]::new);
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
    public void ligarTodosDispositivos(String nome) {
        if(existeDivisao(nome)){
            Divisao divisao = this.divisoes.get(nome);
            divisao.ligaTodosDispositivos();
            this.consumo_energia += divisao.getConsumoEnergia();
        }
    }

    /**
     * Desliga todos os dispositivos de uma divisão da casa.
     *
     * @param nome Nome da divisão.
     */
    public void desligarTodosDispositivos(String nome) {
        if(existeDivisao(nome)){
            Divisao divisao = this.divisoes.get(nome);
            this.consumo_energia -= divisao.getConsumoEnergia();
            divisao.desligaTodosDispositivos();
        }
    }

    /**
     * Liga um aparelho em específico.
     * @param id Identificador do aparelho.
     */
    public void ligarDispositivo(String id) {
        String local = divisaoDoAparelho(id);
        if(local != null){
            Divisao divisao = this.divisoes.get(local);

            this.consumo_energia -= divisao.getConsumoEnergia();
            divisao.ligaDispositivo(id);
            this.consumo_energia += divisao.getConsumoEnergia();
        }
    }

    /**
     * Desliga um aparelho em específico.
     * @param id Identificador do aparelho.
     */
    public void desligarDispositivo(String id) {
        String local = divisaoDoAparelho(id);
        if(local != null){
            Divisao divisao = this.divisoes.get(local);

            this.consumo_energia -= divisao.getConsumoEnergia();
            divisao.desligaDispositivo(id);
            this.consumo_energia += divisao.getConsumoEnergia();
        }
    }

    /**
     * Guarda o identificador da fatura emitida.
     * @param id Identificador da fatura.
     */
    public void guardaFatura(long id){
        this.faturas.add(id);
    }

    /**
     * Identificador da última fatura emitida.
     * @return Identificador da última fatura.
     */
    public long ultimaFatura(){
        return this.faturas.last();
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
                this.numero_dispositivos == casa.numero_dispositivos &&
                this.morada.equals(casa.morada) &&
                this.proprietario.equals(casa.proprietario) &&
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
        sb.append("morada='").append(morada).append('\'');
        sb.append(", proprietario='").append(proprietario).append('\'');
        sb.append(", nif_proprietario=").append(nif_proprietario);
        sb.append(", divisoes=").append(divisoes);
        sb.append(", numero_dispositivos=").append(numero_dispositivos);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(proprietario, nif_proprietario , morada);
    }

    public static Casa parse(String str){
        String[] tokens = str.split("\\{");
        String[] tokens_casa = tokens[0].split(";");
        String[] tokens_divisoes = tokens[1].split("\\|");
        Casa casa = new Casa();
        casa.morada = tokens_casa[0];
        casa.proprietario = tokens_casa[1];
        casa.fornecedor = tokens_casa[2];
        casa.nif_proprietario = Integer.parseInt(tokens_casa[3]);

        Set<Divisao> divisoes = new HashSet<>();

        for (String divisao : tokens_divisoes) {
            divisoes.add(Divisao.parse(divisao));
        }

        casa.setDivisoes(divisoes);

        return casa;
    }
}
