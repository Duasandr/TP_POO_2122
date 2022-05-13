package com.grupo.model;

import com.grupo.comparators.DoubleDecrescente;
import com.grupo.device.SmartDevice;
import com.grupo.house.Casa;
import com.grupo.house.Divisao;
import com.grupo.power.Fatura;
import com.grupo.power.FornecedorEnergia;
import com.grupo.power.FuncaoConsumo;
import exceptions.CasaInexistenteException;
import exceptions.DivisaoInexistenteException;
import exceptions.LinhaFormatadaInvalidaException;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Modelo implements Serializable{
    // Variáveis de instância
    private Map<String , FornecedorEnergia> fornecedores;
    private Map<String , Casa> casas;
    private Map<Long , Fatura> faturas;

    private static LocalDateTime instante_no_tempo = LocalDateTime.now();

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

    public Set<FornecedorEnergia> getFornecedores() {
        Set<FornecedorEnergia> copia = new HashSet<>(this.fornecedores.size());
        for (FornecedorEnergia fornecedor : this.fornecedores.values()) {
            copia.add(fornecedor.clone());
        }
        return copia;
    }

    public Set<Casa> getCasas() {
        Set<Casa> copia = new HashSet<>(this.casas.size());
        for (Casa casa : this.casas.values()) {
            copia.add(casa.clone());
        }
        return copia;
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

    private static void saltaPara(LocalDateTime destino){
        instante_no_tempo = destino;
    }

    public void avancaNoTempo(long dias){
        LocalDateTime destino = instante_no_tempo.plusDays(dias);
        emiteFaturas(destino);
        saltaPara(destino);
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

    public void alteraFornecedorPrecoBase(String fornecedor , double novo_valor){
        if(this.fornecedores.containsKey(fornecedor)){
            this.fornecedores.get(fornecedor).setValorBase(novo_valor);
        }
    }

    public void alteraFuncaoConsumo(String fornecedor , String funcao){
        if(this.fornecedores.containsKey(fornecedor)){
            this.fornecedores.get(fornecedor).setFuncaoConsumo(FuncaoConsumo.parse(funcao));
        }
    }

    public void emiteFaturas(LocalDateTime fim){
        for (Casa casa : this.casas.values()) {
            Fatura fatura = Fatura.emiteFatura(casa,this.fornecedores.get(casa.getFornecedor()),instante_no_tempo,fim);
            this.faturas.put(fatura.getId(),fatura);
        }
    }

    public Fatura casaComMaiorDespesa(){
        double max_despesa = 0.0;
        Fatura maior_despesa = null;

        for (Casa casa : this.casas.values()) {
            Fatura fatura = this.faturas.get(casa.ultimaFatura());
            double despesa = fatura.getTotalAPagar();

            if(despesa > max_despesa){
                max_despesa = despesa;
                maior_despesa = fatura;
            }
        }
        return maior_despesa;
    }

    public FornecedorEnergia fornecedorComMaiorFaturacao(){
        double max_faturacao = 0.0;
        FornecedorEnergia maior_faturacao = null;

        for (FornecedorEnergia fornecedor : this.fornecedores.values()) {

            double faturacao = fornecedor.getFaturacao();
            if(faturacao > max_faturacao){
                max_faturacao = faturacao;
                maior_faturacao = fornecedor;
            }
        }
        return maior_faturacao == null ? null : maior_faturacao.clone();
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

    public void setCasas(List<String> list) throws LinhaFormatadaInvalidaException {
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
