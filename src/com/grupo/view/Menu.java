package com.grupo.view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private Scanner input;
    private String opcao;
    private String ficheiro_texto;

    public Menu(String path){
        this.input = new Scanner(System.in);
        this.ficheiro_texto = path;
        this.opcao = "null";
    }

    public void apresentaMenu() throws IOException {
        List<String> linhas = Files.readAllLines(Path.of(this.ficheiro_texto));
        for (String opcao : linhas) {
            System.out.println(opcao);
        }
        input.next();
    }

    public String getOpcao(){
        return this.opcao;
    }

    public String lerString(){
        return this.input.next();
    }

    public void executa() throws IOException {
        this.opcao = this.input.nextLine();
    }

    public void finalizar(){
        this.input.close();
    }
}
