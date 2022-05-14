package com.grupo;

import com.grupo.controlador.Controlador;
import com.grupo.model.Modelo;
import com.grupo.view.AutoView;
import com.grupo.view.TerminalView;
import com.grupo.view.View;

public class Main {
    public static void main(String[] args) {
        Modelo modelo = new Modelo();
        Controlador controlador = new Controlador(modelo);
        if(args.length > 0){
            try{
                View view = new AutoView ( controlador, args[0] );
                view.executa ();
            }catch ( Exception e ){
                System.out.println ( e.getMessage () );
            }
        }else{
            View view = new TerminalView( controlador);
            view.executa();
        }
    }
}