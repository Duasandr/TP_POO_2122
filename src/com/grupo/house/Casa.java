package com.grupo.house;

import com.grupo.device.SmartDevice;
import com.grupo.power.ContratoEnergia;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Casa {
    //Variáveis de instância

    private String proprietario;
    private int nif_proprietario;
    private HashMap<String , Divisao> divisoes;
    private ContratoEnergia contrato_energia;
    private double consumo_diario;

    //Construtores

    /**
     * Construtor vazio.
     */
    public Casa(){
        this("null",0,null);
    }

    /**
     * Construtor por argumentos.
     * @param proprietario Nome do proprietário.
     * @param nif_proprietario Número de identificação fiscal.
     * @param divisoes Divisões da casa.
     */
    public Casa(String proprietario , int nif_proprietario , Set<Divisao> divisoes ){
        this.setProprietario(proprietario);
        this.setNifProprietario(nif_proprietario);
        this.setDivisoes(divisoes);
        //this.setContratoEnergia(contrato);
        this.atualizaConsumoDiario();
    }

    /**
     * Construtor por cópia.
     * @param casa Casa a copiar.
     */
    public Casa(Casa casa){
        this.setProprietario(casa.getProprietario());
        this.setNifProprietario(casa.getNifProprietario());
        this.setDivisoes((Set<Divisao>) casa.divisoes.values());
        this.setContratoEnergia(casa.contrato_energia);
        this.setConsumoDiario(casa.getConsumoDiario());
    }

    //Métodos de instância

    //Setters

    /**
     * Define o nome do proprietário.
     * @param proprietario Nome.
     */
    private void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    /**
     * Define o NIF do proprietário.
     * @param nif_proprietario Número de identificação fiscal.
     */
    private void setNifProprietario(int nif_proprietario) {
        this.nif_proprietario = nif_proprietario;
    }

    /**
     * Define as divisões e os aparelhos nelas contidos
     * @param divisoes Divisões da casa.
     */
    public void setDivisoes(Set<Divisao> divisoes) {
        HashMap<String , Divisao> copy = new HashMap<>(divisoes.size());
        for (Divisao divisao : divisoes) {
            copy.put(divisao.getNome() , divisao.clone());
        }
        this.divisoes = copy;
    }

    /**
     * Define o contrato de energia da casa.
     * @param contrato_energia Contrato de energia a definir.
     */
    public void setContratoEnergia(ContratoEnergia contrato_energia) {
        this.contrato_energia = contrato_energia.clone();
    }

    /**
     * Define o consumo diário da casa.
     * @param consumo Consumo diário a definir.
     */
    private void setConsumoDiario(double consumo){
        this.consumo_diario = consumo;
    }

    //Getters

    /**
     * Devolve o nome do proprietário.
     * @return Nome do proprietário.
     */
    public String getProprietario() {
        return this.proprietario;
    }

    /**
     * Devolve o número de identificação fiscal do proprietário.
     * @return NIF do proprietário.
     */
    public int getNifProprietario() {
        return this.nif_proprietario;
    }

    /**
     * Devolve um conjunto das divisões da casa.
     * @return Divisoões da casa.
     */
    public Set<Divisao> getDivisoes() {
        HashSet<Divisao> copy = new HashSet<Divisao>(divisoes.size());
        for (Divisao divisao : divisoes.values()) {
            copy.add(divisao.clone());
        }
        return copy;
    }

    /**
     * Devolve o contrato com o fornecedor de energia.
     * @return Contrato de energia.
     */
    public ContratoEnergia getContratoEnergia() {
        return this.contrato_energia.clone();
    }

    /**
     * Devolve o consumo diário da casa.
     * @return Consumo diário.
     */
    public double getConsumoDiario(){
        return this.consumo_diario;
    }

    //Métodos

    /**
     * Testa se uma divisão existe numa casa.
     * @param nome Nome da divisão.
     * @return true - false
     */
    public boolean existeDevisao(String nome){
        return this.divisoes.containsKey(nome);
    }

    /**
     * Adiciona uma nova divisão à casa com os seus aparelhos.
     * @param nome Nome da divisão.
     * @param aparelhos Aparelhos presentes na divisão. Pode ser null.
     */
    public void adicionaDivisao(String nome , Set<SmartDevice> aparelhos){
        if(!existeDevisao(nome)) {
            Divisao divisao = new Divisao(nome, aparelhos);
            divisao.atualizaConsumoEnergia();
            this.divisoes.put(nome, divisao);
            this.atualizaConsumoDiario();
        }
    }

    /**
     * Atualiza o consumo diário da casa.
     */
    public void atualizaConsumoDiario(){
        double consumo = 0.0;
        for (Divisao divisao : this.divisoes.values()) {
            consumo += divisao.getConsumoEnergia();
        }
        this.consumo_diario = consumo;
    }

    /**
     * Representação textual da casa.
     * @return String.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Casa{");
        sb.append("proprietario='").append(proprietario).append('\'');
        sb.append(", nif_proprietario=").append(nif_proprietario);
        sb.append(", divisoes=").append(divisoes);
        sb.append(", contrato_energia=").append(contrato_energia);
        sb.append(", consumo_diario=").append(consumo_diario);
        sb.append('}');
        return sb.toString();
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
                Double.compare(casa.consumo_diario, consumo_diario) == 0 &&
                this.proprietario.equals(casa.proprietario) &&
                this.divisoes.equals(casa.divisoes) &&
                this.contrato_energia.equals(casa.contrato_energia);
    }

    /**
     * Devolve um indíce através de uma função hash.
     * @return Índice.
     */
    @Override
    public int hashCode() {
        return Objects.hash(proprietario, nif_proprietario);
    }
}
