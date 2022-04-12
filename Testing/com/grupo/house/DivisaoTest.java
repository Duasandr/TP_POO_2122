package com.grupo.house;

import com.grupo.device.Fabricante;
import com.grupo.device.SmartDevice;
import com.grupo.device.SmartSpeaker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DivisaoTest {
    private Fabricante fab = new Fabricante("JBL");
    HashSet<SmartDevice> devices = new HashSet<SmartDevice>(10);
    private Divisao divisao;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            SmartDevice device = fab.produzSmartSpeaker("id" + i, true, 120 + i, 59.00, 12+i, "channel", 50);;
            devices.add(device);
        }
        divisao = new Divisao("Cozinha",null);
        divisao.setAparelhos(devices);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void setNome() {
    }

    @Test
    void setAparelhos() {
    }

    @Test
    void setConsumoEnergia() {
    }

    @Test
    void getNome() {
    }

    @Test
    void getAparelhos() {
    }

    @Test
    void getConsumoEnergia() {
    }

    @Test
    void atualizaConsumoEnergia() {
    }

    @Test
    void existeAparelho() {
        assertTrue(divisao.existeAparelho("id0"), "Tem de existir.");
    }

    @Test
    void alteraEstados() {
    }

    @Test
    void alteraUnicoEstado() {
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

    @Test
    void testToString() {
        System.out.println(divisao.toString());
    }
}