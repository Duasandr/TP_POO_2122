package com.grupo.house;

import com.grupo.exceptions.LinhaFormatadaInvalidaException;
import org.junit.jupiter.api.Test;

class DivisaoTest {

    @Test
    void setNome() {
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
    void getNumeroDispositivos() {
    }

    @Test
    void existeAparelho() {
    }

    @Test
    void ligaTodosDispositivos() {
    }

    @Test
    void desligaTodosDispositivos() {
    }

    @Test
    void ligaDispositivo() {
    }

    @Test
    void desligaDispositivo() {
    }

    @Test
    void testClone() {
    }

    @Test
    void testToString() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testParse() throws LinhaFormatadaInvalidaException {
        Divisao divisao = Divisao.parse("Sala[Speaker:12345;desligado;12.00;25;MEGA;100 Camera:8927;ligado;12.00;1024;50.0");
        System.out.println(divisao);
    }
}