package com.grupo.controlador;

import com.grupo.device.SmartDevice;
import com.grupo.exceptions.*;
import com.grupo.generator.GeradorAleatorio;
import com.grupo.house.Casa;
import com.grupo.model.Modelo;
import com.grupo.power.FornecedorEnergia;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class Controlador {
    private Modelo modelo;

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
        Set<FornecedorEnergia> fornnecedores = this.modelo.getFornecedores();
        sb.append("Fornecedores: {\n");
        for (FornecedorEnergia fornecedor : fornnecedores) {
            sb.append("\t").append(fornecedor.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

    public String statusCasas(){
        StringBuilder sb = new StringBuilder();
        Set<Casa> casas = this.modelo.getCasas();
        sb.append("Casas: {\n");
        for (Casa casa : casas) {
            sb.append("\t").append(casa.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

    private String status() {
        return statusFornecedores() + statusCasas();
    }

    public void loaderHandler(String[] op) throws IOException, ClassNotFoundException, LinhaFormatadaInvalidaException, OpcaoInvalidaException, SmartDeviceInvalidoException, TonalidadeInvalidaException, EstadoInvalidoException {
        switch (op[1]){
            case "-bin" -> carregaFicheiroBinario();
            case "-txt" -> carregaDadosFicheiroTexto();
            case "-rand" -> geraDadosAleatorios(new GeradorAleatorio());
            default -> throw new OpcaoInvalidaException(op[0]);
        }
    }

    public String statusHandler(String[] op) throws OpcaoInvalidaException {
        return switch (op[1]){
            case "-all" -> status();
            case "-f" -> statusFornecedores();
            case "-h" -> statusCasas();
            default -> throw new OpcaoInvalidaException(op[0]);
        };
    }

    public void modifyHandler(String[] args) throws OpcaoInvalidaException, CasaInexistenteException, DivisaoInexistenteException {
        switch (args[1]){
            case "-allon" -> this.modelo.alteraEstado(SmartDevice.Estado.LIGADO);
            case "-allof" -> this.modelo.alteraEstado(SmartDevice.Estado.DESLIGADO);
            case "-divon" -> this.modelo.alteraEstado(args[2],args[3], SmartDevice.Estado.LIGADO);
            case "-divof" -> this.modelo.alteraEstado(args[2],args[3], SmartDevice.Estado.DESLIGADO);
            case "-h" -> statusCasas();
            default -> throw new OpcaoInvalidaException(args[0]);
        }
    }

    public String mvpHandler(String[] args) {
        return switch (args[1]){
            case "-h" -> this.modelo.casaComMaiorDespesa().getMorada().toString();
            default -> "";
        };
    }


    public String fornecedorComMaiorFaturacao(){
        FornecedorEnergia fornecedor = this.modelo.fornecedorComMaiorFaturacao();
        return fornecedor.getNome() + "\n Faturação: " + fornecedor.getFaturacao();
    }

    public void avancaNoTempo(int dias){
        this.modelo.avancaNoTempo(dias);
    }
}
