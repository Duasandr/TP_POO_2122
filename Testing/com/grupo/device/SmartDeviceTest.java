package com.grupo.device;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SmartDeviceTest {
    private  String[] ids = new String[10];
    private  Boolean[] estados = new Boolean[10];
    private  int[] potencias = new int[10];
    private  Double[] precos = new Double[10];
    private  Fabricante fab = new Fabricante("Toshiba");
    private  ArrayList<SmartDevice> devices = new ArrayList<SmartDevice>(10);

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            ids[i] = "XWZ" + i;
            estados[i] =  ((i & 1) == 0);
            potencias[i] = 10 + (i*2);
            precos[i] = 2.5 + (i*2);
            SmartDevice device = i % 5 == 0 ? fab.produzSmartCamera(ids[i],estados[i],potencias[i],precos[i],720,1024.00) : (i & 1) == 0 ? fab.produzSmartBulb(ids[i],estados[i],potencias[i],precos[i],0,0.2+i) : fab.produzSmartSpeaker(ids[i],estados[i],potencias[i],precos[i],0,"Canal"+i,20+i);
            devices.add(device);
        }
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void testGetIdFabricante() {
        String[] actual = new String[10];
        Set<String> result = devices.stream().map(SmartDevice::getIdFabricante).collect(Collectors.toSet());
        result.toArray(actual);
        assertArrayEquals(ids, actual ,"Identificadores têm de ser iguais.\n");
    }

    @org.junit.jupiter.api.Test
    void testGetEstado() {
        Boolean[] actual = new Boolean[10];
        Set<Boolean> result = devices.stream().map(SmartDevice::getEstado).collect(Collectors.toSet());
        result.toArray(actual);
        assertArrayEquals(estados,actual,"Estados têm de ser iguais.\n");
    }

    @org.junit.jupiter.api.Test
    void testGetPrecoInstalacao() {
        Double[] actual = new Double[10];
        Set<Double> result = devices.stream().map(SmartDevice::getPrecoInstalacao).collect(Collectors.toSet());
        result.toArray(actual);
        assertArrayEquals(precos,actual,"Preços têm de ser iguais.\n");
    }

    @org.junit.jupiter.api.Test
    void testSetIdFabricante() {
        int i = 9 , j = 0;
        String[] expected = new String[10];
        for (String id: ids) {
            expected[i--] = id;
            devices.get(j++).setIdFabricante(id);
        }
        String[] actual = new String[10];
        Set<String> result = devices.stream().map(SmartDevice::getIdFabricante).collect(Collectors.toSet());
        result.toArray(actual);
        assertArrayEquals(expected,actual,"Ids têm de ser iguais.\n");
    }

    @org.junit.jupiter.api.Test
    void testSetEstado() {
        int i = 9 , j = 0;
        Boolean[] expected = new Boolean[10];
        for (Boolean estado: estados) {
            expected[i--] = estado;
            devices.get(j++).setEstado(estado);
        }
        Boolean[] actual = new Boolean[10];
        Set<Boolean> result = devices.stream().map(SmartDevice::getEstado).collect(Collectors.toSet());
        result.toArray(actual);
        assertArrayEquals(expected,actual,"Preços têm de ser iguais.\n");
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
        System.out.println(devices.get(0).toString());
    }

    @org.junit.jupiter.api.Test
    void testHashCode() {
    }
}