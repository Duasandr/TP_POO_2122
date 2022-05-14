package com.grupo.controlador;

import com.grupo.comparators.FornecedorPorFaturacao;
import com.grupo.device.SmartBulb;
import com.grupo.device.SmartCamera;
import com.grupo.device.SmartDevice;
import com.grupo.device.SmartSpeaker;
import com.grupo.exceptions.*;
import com.grupo.generator.GeradorAleatorio;
import com.grupo.house.Casa;
import com.grupo.model.Modelo;
import com.grupo.power.Fatura;
import com.grupo.power.FornecedorEnergia;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.List;

public class Controlador {
    private Modelo modelo;
    private long dias;

    public Controlador(Modelo modelo){
        this.modelo = modelo;
    }

    private static List<String> lerFicheiro(String path) throws IOException {
        return Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
    }

    private void carregaFornecedores(String path) throws IOException {
        this.modelo.setFornecedores(lerFicheiro(path));
    }

    private void carregaCasas(String path) throws IOException, LinhaFormatadaInvalidaException, SmartDeviceInvalidoException, TonalidadeInvalidaException, EstadoInvalidoException {
        this.modelo.setCasas(lerFicheiro(path));
    }

    public void guardaEstado(String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this.modelo);
        oos.flush();
        oos.close();
    }

    private static Modelo carregaEstado(String path) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Modelo s = (Modelo) ois.readObject();
        ois.close();
        return s;
    }

    private void carregaFicheiroBinario() throws IOException, ClassNotFoundException {
        this.modelo = carregaEstado("./bin/estado");
    }

    private void carregaDadosFicheiroTexto() throws IOException, LinhaFormatadaInvalidaException, SmartDeviceInvalidoException, TonalidadeInvalidaException, EstadoInvalidoException {
        carregaFornecedores("./in/fornecedores");
        carregaCasas("./in/casas");
    }

    private void geraDadosAleatorios(GeradorAleatorio gerador){
        this.modelo.setFornecedores(gerador.getFornecedores());
        this.modelo.setCasas(gerador.getCasas());
    }


    public String statusFornecedores(){
        StringBuilder sb = new StringBuilder();
        Iterator<FornecedorEnergia> fornecedores = this.modelo.getFornecedor();
        sb.append("Fornecedores: {\n");
        while(fornecedores.hasNext()) {
            sb.append("\t").append(fornecedores.next().toString()).append("\n");
        }
        sb.append("}\n");
        return sb.toString();
    }

    public String statusCasas(){
        StringBuilder sb = new StringBuilder();
        Iterator<Casa> casas = this.modelo.getCasa();
        sb.append("Casas: {\n");
        while(casas.hasNext()) {
            sb.append("\t").append(casas.next().toString()).append("\n");
        }
        sb.append("}\n");
        return sb.toString();
    }

    private String status() {
        return statusFornecedores() + statusCasas();
    }

    public void loaderHandler(String[] args) throws IOException, ClassNotFoundException, LinhaFormatadaInvalidaException, OpcaoInvalidaException, SmartDeviceInvalidoException, TonalidadeInvalidaException, EstadoInvalidoException {
        if(args.length > 1){
        switch (args[1]){
            case "-bin" -> carregaFicheiroBinario();
            case "-txt" -> carregaDadosFicheiroTexto();
            case "-rand" -> geraDadosAleatorios(new GeradorAleatorio());
            default -> throw new OpcaoInvalidaException(args[0]);
        }
        }else {
            throw new OpcaoInvalidaException("man sim para qualquer duvida");
        }
    }

    public String statusHandler(String[] args) throws OpcaoInvalidaException {
        if(args.length > 1){
        return switch (args[1]){
            case "-all" -> status();
            case "-f" -> statusFornecedores();
            case "-h" -> statusCasas();
            default -> throw new OpcaoInvalidaException(args[0]);
        };
        }else {
            throw new OpcaoInvalidaException("man sim para qualquer duvida");
        }
    }

    public void modifyHandler(String[] args) throws OpcaoInvalidaException, CasaInexistenteException, DivisaoNaoExisteException {
        if(args.length > 1){
        switch (args[1]){
            case "-allon" -> this.modelo.alteraEstado(SmartDevice.Estado.LIGADO);
            case "-allof" -> this.modelo.alteraEstado(SmartDevice.Estado.DESLIGADO);
            case "-divon" -> this.modelo.alteraEstado(args[2],args[3], SmartDevice.Estado.LIGADO);
            case "-divof" -> this.modelo.alteraEstado(args[2],args[3], SmartDevice.Estado.DESLIGADO);
            case "-h" -> statusCasas();
            default -> throw new OpcaoInvalidaException(args[0]);
        }
    }else {
        throw new OpcaoInvalidaException("man sim para qualquer duvida");
    }
    }

    public String mvpHandler(String[] args) throws OpcaoInvalidaException, DadosInsuficientesException {
        if(args.length > 1){
            if(this.dias > 0) {
                return switch (args[1]) {
                    case "-h" -> casaComMaiorDespesa();
                    case "-f" -> fornecedorComMaiorFaturacao();
                    default -> throw new OpcaoInvalidaException(args[0]);
                };
            }else{
                throw new DadosInsuficientesException("Whoops");
            }
        }else {
            throw new OpcaoInvalidaException("man sim para qualquer duvida");
        }
    }

    public void devHandler(String[] args) throws OpcaoInvalidaException, CasaInexistenteException, DivisaoNaoExisteException, DispositivoNaoExisteException, TonalidadeInvalidaException, NaoEBulbException, NaoECamException, NaoESpeakException {
        AbstractMap.SimpleEntry<String , SmartDevice> dev = this.modelo.removeDispositivo(args[2],args[3]);
        switch (args[1]){
            case "-on" -> {
                dev.getValue().setEstado(SmartDevice.Estado.LIGADO);
                this.modelo.adicionaDispositivo(args[2], dev.getKey() , dev.getValue());
            }
            case "-off" -> {
                dev.getValue().setEstado(SmartDevice.Estado.DESLIGADO);
                this.modelo.adicionaDispositivo(args[2], dev.getKey() , dev.getValue());
            }
            case "-price" -> {
                dev.getValue().setPrecoInstalacao(Double.parseDouble(args[4]));
                this.modelo.adicionaDispositivo(args[2], dev.getKey() , dev.getValue());
            }
            case "-tone" -> {
                if (dev.getValue() instanceof SmartBulb) {
                    ((SmartBulb) dev.getValue()).setTonalidade(SmartBulb.parseTonalidade(args[4]));
                } else {
                    throw new NaoEBulbException(args[3]);
                }
                this.modelo.adicionaDispositivo(args[2], dev.getKey() , dev.getValue());
            }
            case "-dim" -> {
                if(dev.getValue() instanceof SmartBulb){
                    ((SmartBulb) dev.getValue()).setDimensao(Double.parseDouble(args[4]));
                }else{
                    throw new NaoEBulbException(args[3]);
                }
                this.modelo.adicionaDispositivo(args[2], dev.getKey() , dev.getValue());
            }
            case "-res" -> {
                if(dev.getValue() instanceof SmartCamera){
                    ((SmartCamera) dev.getValue()).setResolucao(Integer.parseInt(args[4]));
                }else{
                    throw new NaoECamException(args[3]);
                }
                this.modelo.adicionaDispositivo(args[2], dev.getKey() , dev.getValue());
            }
            case "-file" -> {
                if(dev.getValue() instanceof SmartCamera){
                    ((SmartCamera) dev.getValue()).setTamanhoFicheiro(Double.parseDouble(args[4]));
                }else{
                    throw new NaoECamException(args[3]);
                }
                this.modelo.adicionaDispositivo(args[2], dev.getKey() , dev.getValue());
            }
            case "-vol" -> {
                if(dev.getValue() instanceof SmartSpeaker){
                    ((SmartSpeaker) dev.getValue()).setVolume(Integer.parseInt(args[4]));
                }else{
                    throw new NaoESpeakException(args[3]);
                }
                this.modelo.adicionaDispositivo(args[2], dev.getKey() , dev.getValue());
            }
            case "-max" -> {
                if(dev.getValue() instanceof SmartSpeaker){
                    ((SmartSpeaker) dev.getValue()).setVolumeMaximo(Integer.parseInt(args[4]));
                }else{
                    throw new NaoESpeakException(args[3]);
                }
                this.modelo.adicionaDispositivo(args[2], dev.getKey() , dev.getValue());
            }
            case "-cha" -> {
                if(dev.getValue() instanceof SmartSpeaker){
                    ((SmartSpeaker) dev.getValue()).setCanal(args[4]);
                }else{
                    throw new NaoESpeakException(args[3]);
                }
                this.modelo.adicionaDispositivo(args[2], dev.getKey() , dev.getValue());
            }
            default -> throw new OpcaoInvalidaException(args[0]);
        }
    }

    public void avancaNoTempo(long dias){
        LocalDateTime inicio = LocalDateTime.now().plusDays(this.dias);
        this.dias += dias;
        LocalDateTime fim = LocalDateTime.now().plusDays(this.dias);
        this.modelo.emiteFaturas(inicio , fim);
    }

    private String darOrdenacao(LocalDateTime inicio , LocalDateTime fim){
        StringBuilder sb = new StringBuilder();
        Iterator<Casa> casas = this.modelo.getCasa(inicio,fim);

        while(casas.hasNext()){
            sb.append(casas.next().toString()).append("\n");
        }
        sb.append("}");

        return sb.toString();
    }

    public String darHandler(String[] args) throws OpcaoInvalidaException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return darOrdenacao(LocalDateTime.from(LocalDate.parse(args[1] , formatter).atStartOfDay()), LocalDateTime.from(LocalDate.parse(args[2] , formatter).atStartOfDay()));
    }

    public String billHandler(String[] args){
        return listarFaturasFornecedor(args[1]);
    }

    public void deloreanHandler(String[] args){
        avancaNoTempo(Long.parseLong(args[1]));
    }

    public String casaComMaiorDespesa(){
        Iterator<Casa> casas = this.modelo.getCasa();
        Casa mvp = casas.next();
        double max_despesa = this.modelo.getFatura(mvp.ultimaFatura()).getTotalAPagar();

        while(casas.hasNext()) {
            Casa casa = casas.next();
            Fatura fatura = this.modelo.getFatura(casa.ultimaFatura());
            double despesa = fatura.getTotalAPagar();

            if(despesa > max_despesa){
                max_despesa = despesa;
                mvp = casa;
            }
        }

        return "Casa: " + mvp.getMorada() + "\nDespesa: " + max_despesa;
    }

    public String fornecedorComMaiorFaturacao(){
        Iterator<FornecedorEnergia> fornecedores = this.modelo.getFornecedor(new FornecedorPorFaturacao());
        FornecedorEnergia mvp = fornecedores.next();
        return "Fornecedor: " + mvp.getNome() + "\nFaturação: " + mvp.getFaturacao();
    }

    public String listarFaturasFornecedor(String nome){
        StringBuilder sb = new StringBuilder("Fornecedor: " + nome + "{\n");
        Iterator<Fatura> faturas = this.modelo.getFatura(nome);

        while(faturas.hasNext()){
            sb.append(faturas.next().toString()).append("\n");
        }
        sb.append("}");

        return sb.toString();
    }
}
