package com.grupo;

import com.grupo.controlador.Controlador;
import com.grupo.view.View;
import com.grupo.model.Modelo;
import com.grupo.exceptions.LinhaFormatadaInvalidaException;

import java.io.IOException;

public class TP_POO {
    public static void main(String[] args) throws IOException, ClassNotFoundException, LinhaFormatadaInvalidaException {
        Modelo modelo = new Modelo();
        Controlador controlador = new Controlador(modelo);
        View view = new View(controlador);
        view.executa();
    }
}