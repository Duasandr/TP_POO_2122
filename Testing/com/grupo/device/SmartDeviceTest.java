package com.grupo.device;

import static org.junit.jupiter.api.Assertions.*;

class SmartDeviceTest {
    Fabricante fab1 = new Fabricante("Toshiba");
    SmartDevice device1 = new SmartBulb("XWZ123",false,40,2.5,0,5);
    SmartDevice device2 = new SmartSpeaker("XWZ12345",true,120,25.00,0,"Não selecionado",20);
    SmartDevice device3 = new SmartCamera("XWZ123456",false,220,50.00,720,1024.00);

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void testGetIdFabricante() {
        String[] expected = {"XWZ123" , "XWZ12345" , "XWZ123456"};
        String[] actual = {device1.getIdFabricante() , device2.getIdFabricante() , device3.getIdFabricante()};
        assertArrayEquals(expected,actual,"Identificadores têm de ser iguais.");
    }

    @org.junit.jupiter.api.Test
    void testGetEstado() {
        boolean[] expected = {false , true , false};
        boolean[] actual = {device1.getEstado() , device2.getEstado() , device3.getEstado()};
        assertArrayEquals(expected,actual,"Estados têm de ser iguais.");
    }

    @org.junit.jupiter.api.Test
    void testGetPrecoInstalacao() {
        double[] expected = {2.5 , 25.00 , 50.00};
        double[] actual = {device1.getPrecoInstalacao() , device2.getPrecoInstalacao() , device3.getPrecoInstalacao()};
        assertArrayEquals(expected,actual,"Preços têm de ser iguais.");
    }

    @org.junit.jupiter.api.Test
    void testSetIdFabricante() {
    }

    @org.junit.jupiter.api.Test
    void testSetEstado() {
    }

    @org.junit.jupiter.api.Test
    void testSetPrecoInstalacao() {
    }

    @org.junit.jupiter.api.Test
    void testConsumoEnergia() {
    }

    @org.junit.jupiter.api.Test
    void testClone() {
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
    }

    @org.junit.jupiter.api.Test
    void testToString() {
    }

    @org.junit.jupiter.api.Test
    void testHashCode() {
    }
}