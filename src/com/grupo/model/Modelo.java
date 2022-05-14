package com.grupo.model;

import com.grupo.comparators.DoubleDecrescente;
import com.grupo.device.SmartDevice;
import com.grupo.exceptions.*;
import com.grupo.house.Casa;
import com.grupo.power.Fatura;
import com.grupo.power.FornecedorEnergia;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Modelo implements Serializable{
    // Variáveis de instância
    private Map<String , FornecedorEnergia> fornecedores;
    private Map<String , Casa> casas;
    private Map<Long , Fatura> faturas;

    //Construtores

    /**
     * Construtor vazio.
     */
    public Modelo(){
        this.fornecedores = new HashMap<>();
        this.casas = new HashMap<>();
        this.faturas = new HashMap<>();
    }

    /**
     * Construtor por parâmetros.
     * @param fornecedores
     * @param casas
     */
    public Modelo(Collection<FornecedorEnergia> fornecedores , Collection<Casa> casas){
        this();
        this.setFornecedores(fornecedores);
        this.setCasas(casas);
    }

    //Getters

    /**
     * Devolve um iterador para os fornecedores contidos.
     * @return Iterator
     */
    public Iterator<FornecedorEnergia> getFornecedor() {
        return this.fornecedores.values().iterator();
    }

    /**
     * Devolve o fornecedor da casa fornecida.
     * @param casa Casa
     * @return FornecedorEnergia
     */
    public FornecedorEnergia getFornecedor(Casa casa){
        return this.fornecedores.get(casa.getFornecedor()).clone();
    }

    /**
     * Devolve um iterador para uma lista ordenada por um comparador.
     * @param comp Comparator
     * @return Iterator
     */
    public Iterator<FornecedorEnergia> getFornecedor(Comparator<FornecedorEnergia> comp){
        List<FornecedorEnergia> fornecedor = this.fornecedores.values()
                .stream()
                .map(FornecedorEnergia::clone)
                .toList();
        fornecedor.sort(comp);
        return fornecedor.iterator();
    }

    /**
     * Devolve uma casa.
     * @param morada String
     * @return Casa
     */
    public Casa getCasa(String morada){
        return this.casas.get(morada).clone();
    }

    /**
     * Devolve um iterador para as casas contidas.
     * @return Iterator
     */
    public Iterator<Casa> getCasa() {
        return this.casas.values()
                .stream()
                .map(Casa::clone)
                .toList().iterator();
    }

    /**
     * Devolve um iterador para uma lista de casas ordenada por ordem decrescente de consumo entre as datas indicadas.
     * @param inicio LocalDatetime
     * @param fim LocalDateTime
     * @return Iterator
     */
    public Iterator<Casa> getCasa(LocalDateTime inicio , LocalDateTime fim){
        List<Fatura> faturas = this.faturas.values()
                .stream()
                .filter(fatura -> fatura.getInicio().isBefore(fim) && fatura.getFim().isAfter(inicio))
                .map(Fatura::clone)
                .toList();

        Map<Double,Casa> casas = new TreeMap<>(new DoubleDecrescente());
        for (Fatura fatura : faturas) {
            casas.put(fatura.getTotalConsumo(),this.casas.get(fatura.getMorada()).clone());
        }

        return casas.values().iterator();
    }

    /**
     * Devolve uma fatura.
     * @param id_fatura long
     * @return Fatura
     */
    public Fatura getFatura(long id_fatura){
        return this.faturas.get(id_fatura).clone();
    }

    /**
     * Devolve um iterador para as faturas de um fornecedor.
     * @return Iterator
     */
    public Iterator<Fatura> getFatura(String nome_fornecedor){
        return this.faturas.values()
                .stream()
                .filter(fatura -> fatura.getFornecedor().equals(nome_fornecedor))
                .map(Fatura::clone)
                .toList()
                .iterator();
    }

    public void emiteFaturas(LocalDateTime inicio , LocalDateTime fim){
        for (Casa casa : this.casas.values()) {
            FornecedorEnergia fornecedor = this.fornecedores.get(casa.getFornecedor());
            Fatura fatura = new Fatura(casa , fornecedor , inicio , fim);
            this.faturas.put(fatura.getId(), fatura);
        }
    }

    //Setters

    public void setFornecedores(Collection<FornecedorEnergia> fornecedores) {
        HashMap<String,FornecedorEnergia> copia = new HashMap<>(fornecedores.size());
        for (FornecedorEnergia fornecedor : fornecedores) {
            copia.put(fornecedor.getNome() , fornecedor.clone());
        }
        this.fornecedores = copia;
    }

    public void setCasas(Collection<Casa> casas) {
        HashMap<String,Casa> copia = new HashMap<>(casas.size());
        for (Casa casa : casas) {
            copia.put(casa.getMorada() , casa.clone());
        }
        this.casas = copia;
    }

    /**
     * Faz o parse de uma lista de String de FornecedorEnergia
     * @param list List
     */
    public void setFornecedores(List<String> list) {
        for (String str : list) {
            FornecedorEnergia fornecedor = FornecedorEnergia.parse(str);
            this.fornecedores.put(fornecedor.getNome(),fornecedor);
        }
    }


    /**
     * Faz o parse de uma lista de String de Casa
     * @param list List
     */
    public void setCasas(List<String> list) throws LinhaFormatadaInvalidaException, SmartDeviceInvalidoException, TonalidadeInvalidaException, EstadoInvalidoException {
        for (String str : list) {
            Casa casa = Casa.parse(str);
            this.casas.put(casa.getMorada(),casa);
        }
    }

    //Métodos de instância

    /**
     * Altera o estado de todos os aparelhos de todas as casas.
     * @param novo_estado Novo estado.
     */
    public void alteraEstado(SmartDevice.Estado novo_estado){
        for (Casa casa : this.casas.values()) {
            casa.alteraEstado(novo_estado);
        }
    }

    /**
     * Altera o estado de todos os dispositivos de uma casa.
     * @param morada Morada da casa.
     * @param novo_estado Novo estado.
     */
    public void alteraEstado(String morada , SmartDevice.Estado novo_estado) throws CasaInexistenteException {
        if(this.casas.containsKey(morada)){
            this.casas.get(morada).alteraEstado(novo_estado);
        }else{
            throw new CasaInexistenteException();
        }
    }

    /**
     * Altera o estado de todos os dispositivos de uma divisão.
     * @param morada Morada da casa.
     * @param divisao Nome da divisão.
     * @param novo_estado Novo estado.
     * @throws CasaInexistenteException Quando não existe a casa.
     * @throws DivisaoNaoExisteException Quando não existe a divisão.
     */
    public void alteraEstado(String morada , String divisao , SmartDevice.Estado novo_estado) throws CasaInexistenteException, DivisaoNaoExisteException {
        if(this.casas.containsKey(morada)){
            this.casas.get(morada).alteraEstadoDivisao(divisao,novo_estado);
        }else{
            throw new CasaInexistenteException();
        }
    }

    public AbstractMap.SimpleEntry<String , SmartDevice> removeDispositivo(String morada , String id_dispositivo) throws DispositivoNaoExisteException, DivisaoNaoExisteException, CasaInexistenteException {
        SmartDevice dev;
        String local;
        Casa casa;
        if(this.casas.containsKey(morada)){
            casa = this.casas.get(morada);
            local = casa.ondeEsta(id_dispositivo);
            dev = casa.removeDispositivo(id_dispositivo,local);
        }else{
            throw new CasaInexistenteException();
        }
        return new AbstractMap.SimpleEntry<String,SmartDevice>(local,dev);
    }

    public void adicionaDispositivo(String casa , String divisao , SmartDevice device) throws DispositivoNaoExisteException {
        if (this.casas.containsKey(casa)){
            this.casas.get(casa).adicionaDispositivo(divisao,device);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Simulador{");
        sb.append("fornecedores=").append(fornecedores);
        sb.append(", casas=").append(casas);
        sb.append(", faturas=").append(faturas);
        sb.append('}');
        return sb.toString();
    }
}
