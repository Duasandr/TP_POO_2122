package com.grupo.view;

import com.grupo.controlador.Controlador;
import com.grupo.exceptions.OpcaoInvalidaException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public
class AutoView extends View {
    private  Writer writer;
    private  String path;
    private  Controlador controlador;

    public
    AutoView ( Controlador controlador , String path) throws IOException {
        this.controlador = controlador;
        this.path =  path;
        this.writer = new FileWriter ( "./input/log.txt" );
    }


    public
    void executa ( ) {
        try {
            List < String > lines = Files.readAllLines ( Path.of ( this.path ) );

                for (String line : lines) {
                    try {
                        String[] args = line.split (" ");
                        switch ( args[0] ) {
                            case "ld" -> controlador.loaderHandler (args );
                            case "stat" -> this.writer.write ( controlador.statusHandler ( args ) + "\n--------------\n");
                            case "mod" -> this.controlador.modifyHandler ( args );
                            case "delorean" -> this.controlador.deloreanHandler ( args );
                            case "mvp" -> this.writer.write ( controlador.mvpHandler ( args ) + "\n--------------\n");
                            case "dar" -> this.writer.write ( controlador.darHandler ( args ) + "\n--------------\n" );
                            case "bills" -> this.writer.write ( this.controlador.billHandler ( args ) + "\n--------------\n");
                            case "dev" -> this.controlador.devHandler ( args );
                            case "exit" -> controlador.guardaEstado ( "./bin/estado" );
                            default -> throw new OpcaoInvalidaException ( args[0] );
                        }
                    } catch ( Exception e ) {
                        System.out.println ( e.getMessage ( ) );
                        break;
                    }
                }
                this.writer.close ();
                this.controlador.guardaEstado ( "./bin/estado" );
        } catch ( Exception e ) {
            System.out.println ( e.getMessage ( ) );
        }
    }
}
