package com.grupo.simulator;

import com.grupo.comparators.DoubleDecrescente;
import com.grupo.comparators.FaturaPorId;
import com.grupo.house.Casa;
import com.grupo.power.Fatura;
import com.grupo.power.FornecedorEnergia;
import com.grupo.power.FuncaoDesconto;

import java.time.LocalDateTime;
import java.util.*;

public class Simulador {
    // Variáveis de instância
    private HashMap<String , FornecedorEnergia> fornecedores;
    private HashMap<String , Casa> casas;
    private HashMap<Long , Fatura> faturas;

    private static LocalDateTime instante_no_tempo = LocalDateTime.now();

    //Construtores

    /**
     * Construtor vazio.
     */
    public Simulador(){
        this.fornecedores = new HashMap<>();
        this.casas = new HashMap<>();
        this.faturas = new HashMap<>();
    }

    /**
     * Construtor por parâmetros.
     * @param fornecedores
     * @param casas
     */
    public Simulador(Collection<FornecedorEnergia> fornecedores , Collection<Casa> casas){
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

    public Set<Fatura> getFaturas() {
        Set<Fatura> copia = new HashSet<>(this.faturas.size());
        for (Fatura fatura : this.faturas.values()) {
            copia.add(fatura.clone());
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

    public void ligaDispositivo(String casa , String id){
        if(this.casas.containsKey(casa)){
            this.casas.get(casa).ligarDispositivo(id);
        }
    }

    public void desligaDispositivo(String casa , String id){
        if(this.casas.containsKey(casa)){
            this.casas.get(casa).desligarDispositivo(id);
        }
    }

    public void ligarTodosDispositivos(String casa , String divisao){
        if(this.casas.containsKey(casa)){
            this.casas.get(casa).ligarTodosDispositivos(divisao);
        }
    }

    public void desligarTodosDispositivos(String casa , String divisao){
        if(this.casas.containsKey(casa)){
            this.casas.get(casa).desligarTodosDispositivos(divisao);
        }
    }

    public void alteraFornecedorPrecoBase(String fornecedor , double novo_valor){
        if(this.fornecedores.containsKey(fornecedor)){
            this.fornecedores.get(fornecedor).setValorBase(novo_valor);
        }
    }

    public void alteraFornecedorDesconto(String fornecedor , FuncaoDesconto novo_valor){
        if(this.fornecedores.containsKey(fornecedor)){
            this.fornecedores.get(fornecedor).setFuncaoDesconto(novo_valor);
        }
    }

    public void emiteFaturas(LocalDateTime fim){
        for (Casa casa : this.casas.values()) {
            Fatura fatura = new Fatura(casa,this.fornecedores.get(casa.getFornecedor()),instante_no_tempo,fim);
            this.faturas.put(fatura.getId(),fatura);
        }
    }

    public String casaComMaiorDespesa(){
        double max_despesa = 0.0;
        String nome = "";

        for (Casa casa : this.casas.values()) {
            Fatura fatura = this.faturas.get(casa.ultimaFatura());
            double despesa = fatura.getTotalAPagar();

            if(despesa > max_despesa){
                max_despesa = despesa;
                nome = casa.getMorada();
            }
        }
        return "Casa: " + nome + " Despesa: " + max_despesa + " €";
    }

    public String fornecedorComMaiorFaturacao(){
        double max_faturacao = 0.0;
        String nome = "";

        for (FornecedorEnergia fornecedor : this.fornecedores.values()) {

            double faturacao = fornecedor.getFaturacao();
            if(faturacao > max_faturacao){
                max_faturacao = faturacao;
                nome = fornecedor.getNome();
            }
        }
        return "Fonecedor: " + nome + " Faturação: " + max_faturacao + " €";
    }

    public Set<Fatura> getFaturasFornecedor(String nome){
        Set<Fatura> copia = new TreeSet<>(new FaturaPorId());

        if(this.fornecedores.containsKey(nome)) {
            for (FornecedorEnergia fornecedor : this.fornecedores.values()) {
                for (Long id_fatura : fornecedor.getFaturas()) {
                    Fatura fatura = this.faturas.get(id_fatura);
                    copia.add(fatura.clone());
                }
            }
        }
        return  copia;
    }

    public String consumoCasasEntreDatas(LocalDateTime inicio , LocalDateTime fim){
        TreeMap<Double , StringBuilder> contadores = new TreeMap<>(new DoubleDecrescente());

        for (Casa casa : this.casas.values()) {

            double consumo = 0.0;
            for (Long id_fatura : casa.getFaturas()) {

                Fatura fatura = this.faturas.get(id_fatura);
                if(fatura.getInicio().isBefore(fim) && fatura.getFim().isAfter(inicio)){
                    consumo += fatura.getTotalConsumo();
                }
            }

            if (consumo > 0){
                if(contadores.containsKey(consumo)){
                    contadores.get(consumo).append(" , ").append(casa.getMorada());
                }else{
                    StringBuilder sb = new StringBuilder(casa.getMorada());
                    contadores.put(consumo ,sb);
                }
            }
        }

        StringBuilder answer = new StringBuilder("lista\n");
        if(contadores.size() > 0) {
            for (double consumo : contadores.keySet()) {
                answer.append(contadores.get(consumo).toString()).append("\n\t").append("Consumo: ").append(consumo*0.001).append("\n");
            }
        }
        return answer.toString();
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
