package com.grupo.device;

import com.grupo.exceptions.TonalidadeInvalidaException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class SmartBulbTest {
    SmartBulb bulb1 = new SmartBulb("test", SmartDevice.Estado.DESLIGADO,19.99, SmartBulb.Tonalidade.FRIA,0.45);
    SmartBulb bulb2;
    SmartBulb.Tonalidade tone;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTonalidade() {
        assertEquals(SmartBulb.Tonalidade.FRIA,bulb1.getTonalidade());
    }

    @Test
    void getDimensao() {
        assertEquals(0.45,bulb1.getDimensao());
    }

    @Test
    void setTonalidade() {
        bulb1.setTonalidade(SmartBulb.Tonalidade.QUENTE);
        assertEquals(SmartBulb.Tonalidade.QUENTE,bulb1.getTonalidade());
    }

    @Test
    void setDimensao() {
        bulb1.setDimensao(0.5);
        assertEquals(0.5,bulb1.getDimensao());
    }

    @Test
    void getConsumoEnergia() {
        assertEquals(0.0,bulb1.getConsumoEnergia());
        bulb1.setEstado(SmartDevice.Estado.LIGADO);
        assertNotEquals(0.0,bulb1.getConsumoEnergia());
    }

    @Test
    void parseTonalidade() {
        try {
            assertEquals(SmartBulb.Tonalidade.NEUTRA, SmartBulb.parseTonalidade("neutra"));
            SmartBulb.parseTonalidade("super quente");
        } catch (TonalidadeInvalidaException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void parse() {
        /*try {
            bulb2 = SmartBulb.parse("parse_test;ligado;19.25")
            assertEquals(SmartBulb.Tonalidade.NEUTRA, SmartBulb.parseTonalidade("neutra"));
            SmartBulb.parseTonalidade("super quente");
        } catch (TonalidadeInvalidaException e){
            System.out.println(e.getMessage());
        }*/
    }

    @Test
    void testClone() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println(LocalDate.parse("2022-05-01",f));
    }

    @Test
    void testEquals() {
    }

    @Test
    void testToString() {
    }

    @Test
    void testHashCode() {
    }
}