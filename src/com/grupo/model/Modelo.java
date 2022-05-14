package com.grupo.model;

import com.grupo.comparators.DoubleDecrescente;
import com.grupo.device.SmartDevice;
import com.grupo.exceptions.*;
import com.grupo.house.Casa;
import com.grupo.power.Fatura;
import com.grupo.power.FornecedorEnergia;
import com.grupo.power.FuncaoConsumo;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    public Iterator<FornecedorEnergia> getFornecedor() {
        return this.fornecedores.values().iterator();
    }

    public FornecedorEnergia getFornecedor(Casa casa){
        return this.fornecedores.get(casa.getFornecedor()).clone();
    }

    public void emiteFaturas(LocalDateTime inicio , LocalDateTime fim){
        for (Casa casa : this.casas.values()) {
            FornecedorEnergia fornecedor = this.fornecedores.get(casa.getFornecedor());
            Fatura fatura = new Fatura(casa , fornecedor , inicio , fim);
            this.faturas.put(fatura.getId(), fatura);
        }
    }

    public Iterator<Casa> getCasas() {
        return this.casas.values().iterator();
    }

    public Iterator<Fatura> getFatura(String nome){
        return this.faturas.values().stream().filter(fatura -> fatura.getFornecedor().equals(nome)).toList().iterator();
    }

    public Iterator<FornecedorEnergia> getFornecedor(Comparator<FornecedorEnergia> comp){
        List<FornecedorEnergia> fornecedor = new ArrayList<>(this.fornecedores.values().stream().map(FornecedorEnergia::clone).toList());
        fornecedor.sort(comp);
        return fornecedor.iterator();
    }

    public Iterator<Casa> getCasas(LocalDateTime inicio , LocalDateTime fim){
        List<Fatura> faturas = this.faturas.values().stream().filter(fatura -> fatura.getInicio().isBefore(fim) && fatura.getFim().isAfter(inicio)).map(Fatura::clone).toList();
        Map<Double,Casa> casas = new TreeMap<>(new DoubleDecrescente());
        for (Fatura fatura : faturas) {
            casas.put(fatura.getTotalConsumo(),this.casas.get(fatura.getMorada()).clone());
        }
        return casas.values().iterator();
    }

    public Fatura getFatura(long id_fatura){
        return this.faturas.get(id_fatura).clone();
    }

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
     * @throws DivisaoInexistenteException Quando não existe a divisão.
     */
    public void alteraEstado(String morada , String divisao , SmartDevice.Estado novo_estado) throws CasaInexistenteException, DivisaoInexistenteException {
        if(this.casas.containsKey(morada)){
            this.casas.get(morada).alteraEstadoDivisao(divisao,novo_estado);
        }else{
            throw new CasaInexistenteException();
        }
    }



    public Map<FornecedorEnergia , Set<Fatura>> getFaturasFornecedor(String nome){
        Map<FornecedorEnergia , Set<Fatura>> copia = new HashMap<>();

        if(this.fornecedores.containsKey(nome)) {
            for (FornecedorEnergia fornecedor : this.fornecedores.values()) {
                Set<Fatura> faturas_fornecedor = new HashSet<>();

                for (Fatura fatura : this.faturas.values()) {
                    faturas_fornecedor.add(fatura.clone());
                }

                copia.put(fornecedor.clone(),faturas_fornecedor);
            }
        }

        return  copia;
    }

    public Map<Double , Set<Casa>> consumoCasasEntreDatas(LocalDateTime inicio , LocalDateTime fim){
        Map<Double , Set<Casa>> resposta = new TreeMap<>(new DoubleDecrescente());

        for (Casa casa : this.casas.values()) {

            double consumo = 0.0;
            for (Long id_fatura : casa.getFaturas()) {

                Fatura fatura = this.faturas.get(id_fatura);
                if(fatura.getInicio().isBefore(fim) && fatura.getFim().isAfter(inicio)){
                    consumo += fatura.getTotalConsumo();
                }
            }

            if (consumo > 0){
                if(resposta.containsKey(consumo)){
                    resposta.get(consumo).add(casa.clone());
                }else{
                    Set<Casa> conjunto = new HashSet<Casa>();
                    conjunto.add(casa.clone());
                    resposta.put(consumo ,conjunto);
                }
            }
        }

        return resposta;
    }

    public void setFornecedores(List<String> list) {
        for (String str : list) {
            FornecedorEnergia fornecedor = FornecedorEnergia.parse(str);
            this.fornecedores.put(fornecedor.getNome(),fornecedor);
        }
    }

    public void setCasas(List<String> list) throws LinhaFormatadaInvalidaException, SmartDeviceInvalidoException, TonalidadeInvalidaException, EstadoInvalidoException {
        for (String str : list) {
            Casa casa = Casa.parse(str);
            this.casas.put(casa.getMorada(),casa);
        }
    }

    public Casa getCasa(String morada){
        return this.casas.get(morada).clone();
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
