package com.grupo.device;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SmartBulbTest {
    private  String[] ids = new String[10];
    private  String[] estados = new String[10];
    private  int[] potencias = new int[10];
    private  Double[] precos = new Double[10];
    private String[] tonalidades = new String[10];
    private Double[] dimensoes = new Double[10];
    private ArrayList<SmartBulb> devices = new ArrayList<>(10);

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            ids[i] = "XWZ" + i;
            estados[i] =  ((i & 1) == 0) ? "LIGADO" : "DESLIGADO";
            potencias[i] = 10 + (i*2);
            precos[i] = 2.5 + (i*2);
            tonalidades[i] = ((i & 1) == 0) ? "FRIA" : "QUENTE";
            dimensoes[i] = 0.2+i;
            SmartBulb device = new SmartBulb(ids[i],estados[i],potencias[i],precos[i],tonalidades[i],dimensoes[i]);
            devices.add(device);
        }
    }

    @AfterEach
    void tearDown() {
        ids = null;
        estados = null;
        potencias = null;
        precos = null;
        tonalidades = null;
        devices = null;
        dimensoes = null;
    }

    @Test
    void getTonalidade() {
        String[] actual = devices.stream().map(SmartBulb::getTonalidade).toArray(String[]::new);
        assertArrayEquals(tonalidades, actual ,"Tonalidades têm de ser iguais.\n");
    }

    @Test
    void getDimensao() {
        Double[] actual = devices.stream().map(SmartBulb::getDimensao).toArray(Double[]::new);
        assertArrayEquals(dimensoes, actual ,"Dimensões têm de ser iguais.\n");
    }

    @Test
    void mudaTonalidade(){
        SmartBulb device = new SmartBulb();
        device.mudaTonalidade("QUENTE");
        assertEquals("QUENTE",device.getTonalidade());
    }

    @Test
    void testConsumoEnergia() {
    }

    @Test
    void testClone() {
        for (SmartBulb original : devices) {
            SmartBulb clone = original.clone();
            assertNotNull(clone,"não pode ser null\n");
            assertNotSame(clone ,original,"Referências de objetos clonados têm de ser diferentes.\n");
            assertEquals(clone.getClass(),original.getClass(),"Classes têm de ser iguais.\n");
            assertEquals(clone.getIdFabricante(),original.getIdFabricante(),"Conteúdos têm de ser iguais.\n");
            assertEquals(clone.getEstado(),original.getEstado(),"Conteúdos têm de ser iguais.\n");
            assertEquals(clone.getPotencia(),original.getPotencia(),"Conteúdos têm de ser iguais.\n");
            assertEquals(clone.getPrecoInstalacao(),original.getPrecoInstalacao(),"Conteúdos têm de ser iguais.\n");
            assertEquals(clone.getTonalidade(),original.getTonalidade(),"Conteúdos têm de ser iguais.\n");
            assertEquals(clone.getDimensao(),original.getDimensao(),"Conteúdos têm de ser iguais.\n");
        }
    }

    @Test
    void testEquals() {
        SmartBulb a_bulb = new SmartBulb("FOO", "ligado",120,15.00, "neutra",20.00);
        SmartBulb another_bulb = new SmartBulb("BAR", "desligado",120,15.00, "neutra",20.00);
        SmartDevice a_clone = a_bulb.clone();
        assertEquals(a_bulb,a_clone,"Têm de existir compatibilidade entre subclasses e superclasses.\n");
        assertNotEquals(a_bulb,another_bulb,"Campos herdados da super classe devem ser comparados.\n");
    }

    @Test
    void testToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SmartDevice = { id_fabricante= ").append(devices.get(0).getIdFabricante());
        sb.append(" , estado = ").append(devices.get(0).getEstado());
        sb.append(" , preco_instalacao = ").append(devices.get(0).getPrecoInstalacao());
        sb.append(" , potencia = ").append(devices.get(0).getPotencia());
        sb.append("}");
        sb.append("{ Tonalidade: ").append(devices.get(0).getTonalidade());
        sb.append(", Dimensao: ").append(devices.get(0).getDimensao());
        sb.append("}");
        assertEquals(sb.toString(),devices.get(0).toString(),"Campos herdados da super classe devem ser imprimidos.\n");
    }
}