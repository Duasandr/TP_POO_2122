package com.grupo.controlador;

import com.grupo.device.SmartDevice;
import com.grupo.generator.GeradorAleatorio;
import com.grupo.house.Casa;
import com.grupo.power.FornecedorEnergia;
import com.grupo.model.Modelo;
import exceptions.CasaInexistenteException;
import exceptions.DivisaoInexistenteException;
import exceptions.LinhaFormatadaInvalidaException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class Controlador {
    private Modelo modelo;
    private String resposta;

    private final static String MENU_PRINCIPAL = "./input/text/menu_principal";
    private final static String MENU_QUERIES = "./input/text/menu_queries";
    private final static String MENU_INPUT = "./input/text/menu_input";
    private final static String MENU_OUTPUT = "./input/text/menu_output";

    private final static String FICHEIRO_FORNECEDOR = "./input/text/fornecedores";
    private final static String FICHEIRO_CASAS = "./input/text/casas";
    private final static String FICHEIRO_ESTADO = "./input/bin/estado";


    public Controlador(Modelo modelo){
        this.modelo = modelo;
    }

    private static List<String> lerFicheiro(String path) throws IOException {
        return Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
    }

    public void carregaFornecedores(String path) throws IOException {
        this.modelo.setFornecedores(lerFicheiro(path));
    }

    public void carregaCasas(String path) throws IOException, LinhaFormatadaInvalidaException {
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

    public void carregaFicheiroBinario(String path) throws IOException, ClassNotFoundException {
        this.modelo = carregaEstado(path);
    }

    public void geraDadosAleatorios(){
        GeradorAleatorio gerador = new GeradorAleatorio();
        this.modelo.setFornecedores(gerador.getFornecedores());
        this.modelo.setCasas(gerador.getCasas());
    }

    private void estadoBroadcastHandler(String[] argv , SmartDevice.Estado novo_estado) throws CasaInexistenteException, DivisaoInexistenteException {
        switch (argv.length){
            case 2 -> this.modelo.alteraEstado(argv[1],novo_estado);
            case 3 -> this.modelo.alteraEstado(argv[1],argv[2],novo_estado);
            default ->{}
        }
    }

    private void estadoHandler(String args , SmartDevice.Estado novo_estado) throws CasaInexistenteException, DivisaoInexistenteException {
        String[] argv = args.split(" ");
        switch (argv[0]){
            case "-all" -> estadoBroadcastHandler(argv,novo_estado);
            case "-h" -> this.modelo.alteraEstado();
        }
    }

    public void ligarTodosDispositivos(){

    }

    public void desligarTodosDispositivos(){
        this.modelo.alteraEstado(SmartDevice.Estado.DESLIGADO);
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

    public String status(String op){
        String resultado = "null";
        if ("-f".equals(op)) {
            resultado = statusFornecedores();
        }else if("-h".equals(op)){
            resultado = statusCasas();
        }
        return resultado;
    }

    public String fornecedorComMaiorFaturacao(){
        FornecedorEnergia fornecedor = this.modelo.fornecedorComMaiorFaturacao();
        return fornecedor.getNome() + "\n Faturação: " + fornecedor.getFaturacao();
    }

    public void avancaNoTempo(int dias){
        this.modelo.avancaNoTempo(dias);
    }
}
