package com.grupo.house;

import com.grupo.device.Fabricante;
import com.grupo.device.SmartDevice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CasaTest {
    private Fabricante fab = new Fabricante("JBL");
    HashSet<SmartDevice> devices = new HashSet<SmartDevice>(10);
    HashSet<Divisao> divisoes = new HashSet<Divisao>(10);
    Casa casa;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            SmartDevice device = fab.produzSmartSpeaker("id" + i, true, 120 + i, 59.00, 12+i, "channel", 50);
            devices.add(device);
        }

        for (int i = 0; i < 10; i++) {
            Divisao div = new Divisao("Cozinha"+i,devices);
            div.atualizaConsumoEnergia();
            divisoes.add(div);
        }

        casa = new Casa("sandro",12345,divisoes);
        casa.atualizaConsumoDiario();


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setDivisoes() {
    }

    @Test
    void setContratoEnergia() {
    }

    @Test
    void getProprietario() {
    }

    @Test
    void getNifProprietario() {
    }

    @Test
    void getDivisoes() {
    }

    @Test
    void getContratoEnergia() {
    }

    @Test
    void getConsumoDiario() {
    }

    @Test
    void existeDevisao() {
    }

    @Test
    void adicionaDivisao() {
    }

    @Test
    void testToString() {
        System.out.println(casa.toString());
    }

    @Test
    void testClone() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
}