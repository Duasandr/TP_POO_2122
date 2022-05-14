package com.grupo.view;

import com.grupo.controlador.Controlador;

public
class TerminalView extends View {
    private final Controlador controlador;
    private final Menu menu_principal;

    private static final String MENU_PRINCIPAL = "./menus/menu_principal";

    public TerminalView( Controlador controlador){
        this.controlador = controlador;
        this.menu_principal = new Menu(MENU_PRINCIPAL);
    }

    public void executa() {
        do {
            try {
                menu_principal.executa();
                switch (menu_principal.getOpcao()) {
                    case "ld" -> {
                        controlador.loaderHandler(menu_principal.getArg());
                        System.out.println("Loaded!");
                    }
                    case "stat" -> System.out.println(controlador.statusHandler(menu_principal.getArg()));
                    case "mod" -> {
                        System.out.println("Broadcasting...");
                        this.controlador.modifyHandler(menu_principal.getArg());
                        System.out.println("Message received.");
                    }
                    case "delorean" -> {
                        System.out.println("Flux capacitor... check!");
                        this.controlador.deloreanHandler(menu_principal.getArg());
                        System.out.println("Great Scott!!!!");
                    }
                    case "mvp" -> {
                        System.out.println(controlador.mvpHandler(menu_principal.getArg()));
                    }case "dar" -> {
                        System.out.println(controlador.darHandler(menu_principal.getArg()));
                    }
                    case "bills" -> System.out.println(this.controlador.billHandler(menu_principal.getArg()));
                    case "dev" -> {
                        this.controlador.devHandler(menu_principal.getArg());
                        System.out.println("Updated!");
                    }
                    case "exit" -> {
                        menu_principal.finalizar();
                        controlador.guardaEstado("./bin/estado");
                        System.out.println("Laters gators!");
                    }
                    case "man" -> menu_principal.apresentaMenu();
                    default -> System.out.println("Opção inválida. Consultar Sir Manuel escrevendo: man");
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }while (!menu_principal.getOpcao().equals("exit"));
    }
}
