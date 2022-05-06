package com.grupo.device;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartBulbTest extends SmartBulb {

    @Test
    void testgetTonalidade() {
    }

    @Test
    void testgetDimensao() {
    }

    @Test
    void setDimensao() {
    }

    @Test
    void testGetConsumoEnergia() {
    }

    @Test
    void mudaTonalidade() {
    }

    @Test
    void parseTonalidade() {
    }

    @Test
    void parse() {
        SmartBulb bulb = SmartBulb.parse("id1;ligado;12.2;fria;0.23");
        System.out.println(bulb);
    }

    @Test
    void testClone() {
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