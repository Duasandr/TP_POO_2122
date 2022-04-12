package com.grupo.device;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SmartSpeakerTest {
    private  String[] ids = new String[10];
    private  Boolean[] estados = new Boolean[10];
    private  int[] potencias = new int[10];
    private  Double[] precos = new Double[10];
    private Integer[] volumes = new Integer[10];
    private Integer[] maxs = new Integer[10];
    private String[] canais = new String[10];
    private ArrayList<SmartSpeaker> devices = new ArrayList<SmartSpeaker>(10);

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            ids[i] = "XWZ" + i;
            estados[i] =  ((i & 1) == 0);
            potencias[i] = 10 + (i*2);
            precos[i] = 2.5 + (i*2);
            maxs[i] = (i % 100) + 1;
            volumes[i] = maxs[i] % 100;
            canais[i] = ids[i];
            SmartSpeaker device = new SmartSpeaker(ids[i],estados[i],potencias[i],precos[i],volumes[i],ids[i],maxs[i]);
            devices.add(device);
        }
    }

    @AfterEach
    void tearDown() {
        ids = null;
        estados = null;
        potencias = null;
        precos = null;
        volumes = null;
        maxs = null;
        canais = null;
        devices = null;
    }

    @Test
    void getVolume() {
        Integer[] actual = devices.stream().map(SmartSpeaker::getVolume).toArray(Integer[]::new);
        assertArrayEquals(volumes, actual ,"Volumes têm de ser iguais.\n");
    }

    @Test
    void getCanal() {
        String[] actual = devices.stream().map(SmartSpeaker::getCanal).toArray(String[]::new);
        assertArrayEquals(canais, actual ,"Canais têm de ser iguais.\n");
    }

    @Test
    void getVolumeMaximo() {
        Integer[] actual = devices.stream().map(SmartSpeaker::getVolumeMaximo).toArray(Integer[]::new);
        assertArrayEquals(volumes, actual ,"Volumes máximos têm de ser iguais.\n");
    }

    @Test
    void setVolume() {
        int i = 0;
        Boolean[] actual = new Boolean[10];
        for (SmartSpeaker speaker : devices) {
            speaker.setVolume((i & 1) == 0 ? i * speaker.getVolumeMaximo() : -i);
            actual[i] = (speaker.getVolume() >= 0) && (speaker.getVolume() <= speaker.getVolumeMaximo());
            i++;
        }
        Boolean[] expected = new Boolean[10];
        for (i = 0; i < 10 ; i++) {
            expected[i] = true;
        }
        assertArrayEquals(expected, actual ,"Volumes têm de ser maior ou iguais que zero e menores ou iguais que o volume máximo.\n");
    }

    @Test
    void setCanal() {
        int i = 9 , j = 0;
        String[] expected = new String[10];
        for (String canal: canais) {
            expected[i--] = canal;
            devices.get(j++).setCanal(canal);
        }
        String[] actual = Arrays.stream(expected).toArray(String[]::new);
        assertArrayEquals(expected,actual,"Canais têm de ser iguais.\n");
    }

    @Test
    void setVolumeMaximo() {
        int i = 9 , j = 0;
        Integer[] expected = new Integer[10];
        for (Integer max: maxs) {
            expected[i--] = max;
            devices.get(j++).setVolumeMaximo(max);
        }
        Integer[] actual = Arrays.stream(expected).toArray(Integer[]::new);
        assertArrayEquals(expected,actual,"Máximos têm de ser iguais.\n");
    }

    @Test
    void consumoEnergia() {
        Optional<Double> consumo = devices.stream().map(SmartSpeaker::consumoEnergia).reduce(Double::sum);
        System.out.println(consumo);
    }

    @Test
    void testClone() {
        for (SmartSpeaker original : devices) {
            SmartSpeaker clone = original.clone();
            assertNotNull(clone,"não pode ser null\n");
            assertNotSame(clone ,original,"Referências de objetos clonados têm de ser diferentes.\n");
            assertEquals(clone.getClass(),original.getClass(),"Classes têm de ser iguais.\n");
            assertEquals(clone.getIdFabricante(),original.getIdFabricante(),"Conteúdos têm de ser iguais.\n");
            assertEquals(clone.getEstado(),original.getEstado(),"Conteúdos têm de ser iguais.\n");
            assertEquals(clone.getPotencia(),original.getPotencia(),"Conteúdos têm de ser iguais.\n");
            assertEquals(clone.getPrecoInstalacao(),original.getPrecoInstalacao(),"Conteúdos têm de ser iguais.\n");
            assertEquals(clone.getVolume(),original.getVolume(),"Conteúdos têm de ser iguais.\n");
            assertEquals(clone.getVolumeMaximo(),original.getVolumeMaximo(),"Conteúdos têm de ser iguais.\n");
            assertEquals(clone.getCanal(),original.getCanal(),"Conteúdos têm de ser iguais.\n");
        }
    }

    @Test
    void testEquals() {
        SmartSpeaker a_speaker = new SmartSpeaker("FOO",true,120,30.00,15,"BAR",20);
        SmartSpeaker another_speaker = new SmartSpeaker("BAR",false,115,30.00,15,"BAR",20);
        SmartDevice a_clone = a_speaker.clone();
        assertEquals(a_speaker,a_clone,"Têm de existir compatibilidade entre subclasses e superclasses.\n");
        assertNotEquals(a_speaker,another_speaker,"Campos herdados da super classe devem ser comparados.\n");
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SmartDevice = { id_fabricante= ").append(devices.get(0).getIdFabricante());
        sb.append(" , estado = ").append(devices.get(0).getEstado());
        sb.append(" , preco_instalacao = ").append(devices.get(0).getPrecoInstalacao());
        sb.append(" , potencia = ").append(devices.get(0).getPotencia());
        sb.append("}");
        sb.append("{ Volume: ").append(devices.get(0).getVolume());
        sb.append(", Canal: ").append(devices.get(0).getCanal());
        sb.append(", Volume Máximo: ").append(devices.get(0).getVolumeMaximo());
        sb.append(" }");
        assertEquals(sb.toString(),devices.get(0).toString(),"Campos herdados da super classe devem ser imprimidos.\n");
    }
}