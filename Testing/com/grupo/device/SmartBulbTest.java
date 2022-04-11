package com.grupo.device;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SmartBulbTest {
    private  String[] ids = new String[10];
    private  Boolean[] estados = new Boolean[10];
    private  int[] potencias = new int[10];
    private  Double[] precos = new Double[10];
    private Integer[] tonalidades = new Integer[10];
    private Double[] dimensoes = new Double[10];
    private  Fabricante fab = new Fabricante("Toshiba");
    private ArrayList<SmartBulb> devices = new ArrayList<SmartBulb>(10);

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            ids[i] = "XWZ" + i;
            estados[i] =  ((i & 1) == 0);
            potencias[i] = 10 + (i*2);
            precos[i] = 2.5 + (i*2);
            tonalidades[i] = i % 10;
            SmartBulb device = new SmartBulb(ids[i],estados[i],potencias[i],precos[i],tonalidades[i],0.2+i);
            devices.add(device);
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTonalidade() {
        Integer[] actual = new Integer[10];
        Set<Integer> result = devices.stream().map(SmartBulb::getTonalidade).collect(Collectors.toSet());
        result.toArray(actual);
        assertArrayEquals(tonalidades, actual ,"Tonalidades têm de ser iguais.\n");
    }

    @Test
    void getDimensao() {
        Double[] actual = new Double[10];
        Set<Double> result = devices.stream().map(SmartBulb::getDimensao).collect(Collectors.toSet());
        result.toArray(actual);
        assertArrayEquals(ids, actual ,"Dimensões têm de ser iguais.\n");
    }

    @Test
    void setTonalidade() {
        int i = 9 , j = 0;
        Integer[] expected = new Integer[10];
        for (Integer tonalidade: tonalidades) {
            expected[i--] = tonalidade;
            devices.get(j++).setTonalidade(tonalidade);
        }
        Integer[] actual = new Integer[10];
        Set<Integer> result = devices.stream().map(SmartBulb::getTonalidade).collect(Collectors.toSet());
        result.toArray(actual);
        assertArrayEquals(expected,actual,"Tonalidades têm de ser iguais.\n");
    }

    @Test
    void setDimensao() {
        int i = 9 , j = 0;
        Double[] expected = new Double[10];
        for (Double dimensao: dimensoes) {
            expected[i--] = dimensao;
            devices.get(j++).setDimensao(dimensao);
        }
        Integer[] actual = new Integer[10];
        Set<Integer> result = devices.stream().map(SmartBulb::getTonalidade).collect(Collectors.toSet());
        result.toArray(actual);
        assertArrayEquals(expected,actual,"Dimensoes têm de ser iguais.\n");
    }

    @Test
    void testConsumoEnergia() {
    }

    @Test
    void testClone() {
        for (SmartBulb original: devices) {
            SmartBulb clone = original.clone();
            assertNotNull(clone);
            assertNotSame(clone, original);
            assert(original.equals(clone));
        }
    }

    @Test
    void testEquals() {

    }

    @Test
    void testToString() {

    }
}