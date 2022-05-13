package com.grupo.view;

import com.grupo.controlador.Controlador;
import com.grupo.exceptions.LinhaFormatadaInvalidaException;

import java.io.IOException;

public class View {
    private Controlador controlador;
    private Menu menu_principal;

    private static final String MENU_PRINCIPAL = "./menus/menu_principal";

    public View(Controlador controlador){
        this.controlador = controlador;
        this.menu_principal = new Menu(MENU_PRINCIPAL);
    }

    public void executa() throws ClassNotFoundException, IOException, LinhaFormatadaInvalidaException {
        do {
            menu_principal.executa();
            switch (menu_principal.getOpcao()) {
                case "ld" -> {
                    System.out.println("Introduza o ficheiro binario.");
                    controlador.carregaFicheiroBinario(menu_principal.lerString());
                    System.out.println("Loaded!");
                }
                case "ld -txt" -> {
                    System.out.println("Introduza o ficheiro contendo fornecedores.");
                    controlador.carregaFornecedores(menu_principal.lerString());
                    System.out.println("Introduza o ficheiro contendo casas.");
                    controlador.carregaCasas(menu_principal.lerString());
                    System.out.println("Loaded... slowly.");
                }
                case "ld -rand" -> {
                    controlador.geraDadosAleatorios();
                    System.out.println("Mayhem awaits.");
                }
                case "on -all" -> {
                    controlador.ligarTodosDispositivos();
                    System.out.println("Let there be light!");
                }
                case "off -all" -> {
                    controlador.desligarTodosDispositivos();
                    System.out.println("Embrace the darkness.");
                }
                case "status -f" ->{
                    System.out.println(this.controlador.status("-f"));
                }
                case "status -h" ->{
                    System.out.println(this.controlador.status("-f"));
                }
                case "exit" -> {
                    menu_principal.finalizar();
                    System.out.println("Laters gators!");
                }
                case "man sim" -> {menu_principal.apresentaMenu();}
                default -> System.out.println("Opção inválida. Consultar Sir Manuel escrevendo: man sim");
            }
        }while (!menu_principal.getOpcao().equals("exit"));
    }
}
