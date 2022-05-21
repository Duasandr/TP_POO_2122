package com.grupo.controlador;

import com.grupo.exceptions.*;
import com.grupo.house.Casa;
import com.grupo.model.Modelo;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ControladorTest {

    @Test
    public void testAddHandler()  {
        Modelo m = new Modelo (  );
        Controlador c = new Controlador ( m );
        String[] add_f = { "add" , "-f" , "EDP;0.13;0.13;Funcaopadrao"};
        String[] add_h = { "add" , "-h" , "Casa0;Pessoa0;EDP;12345{"};
        String[] add_div = { "add" , "-div" , "Casa0" ,"Sala["};
        String[] add_dev_b = { "add" , "-dev" , "Casa0" ,"Sala" , "Bulb:bulb0;ligado;19.99;neutra;0.62"};
        String[] add_dev_s = { "add" , "-dev" , "Casa0" ,"Sala" , "Speaker:speaker0;ligado;19.99;0;RFM;50"};
        String[] add_dev_c = { "add" , "-dev" , "Casa0" ,"Sala" , "Camera:cam0;ligado;19.99;720;1024"};

        try {
            c.addHandler ( add_f );
            c.addHandler ( add_h );
            c.addHandler ( add_div );
            c.addHandler ( add_dev_b );
            c.addHandler ( add_dev_s );
            c.addHandler ( add_dev_c );
        }catch ( Exception e ){
            System.out.println ( e.getMessage () );
            e.printStackTrace ();
        }

        assertEquals ( "Casa0" , m.getCasa ().next ().getMorada () );
        try {
            assertEquals ( "SmartBulb" , m.getCasa ().next ().getDivisoes ().iterator ().next ().getDispositivo ( "bulb0" ).getClass ().getSimpleName () );
            assertEquals ( "SmartSpeaker" , m.getCasa ().next ().getDivisoes ().iterator ().next ().getDispositivo ( "speaker0" ).getClass ().getSimpleName () );
            assertEquals ( "SmartCamera" , m.getCasa ().next ().getDivisoes ().iterator ().next ().getDispositivo ( "cam0" ).getClass ().getSimpleName () );
        } catch ( DispositivoNaoExisteException e ) {
            e.printStackTrace ( );
        }
    }

    @Test
    public void testDar(){
        Modelo m = new Modelo (  );
        Controlador c = new Controlador ( m );
        String[] ld = { "ld" , "-rand"};
        String[] delorean = { "delorean" , "1"};
        String[] dar = { "dar" , "2022-05-1" , "2022-06-01"};

        try {
            c.loaderHandler ( ld );
        } catch ( IOException e ) {
            e.printStackTrace ( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace ( );
        } catch ( LinhaFormatadaInvalidaException e ) {
            e.printStackTrace ( );
        } catch ( OpcaoInvalidaException e ) {
            e.printStackTrace ( );
        } catch ( SmartDeviceInvalidoException e ) {
            e.printStackTrace ( );
        } catch ( TonalidadeInvalidaException e ) {
            e.printStackTrace ( );
        } catch ( EstadoInvalidoException e ) {
            e.printStackTrace ( );
        }

        for (int i = 0; i < 5; i++) {
            c.deloreanHandler ( delorean );
        }

        System.out.println ( c.darHandler ( dar ) );
    }
}