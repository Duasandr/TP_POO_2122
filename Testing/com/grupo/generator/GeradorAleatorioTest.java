package com.grupo.generator;

import com.grupo.model.Modelo;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class GeradorAleatorioTest {

    @Test
    void getCasas() {
        GeradorAleatorio gerador = new GeradorAleatorio();
        Modelo sim = new Modelo(gerador.getFornecedores(),gerador.getCasas());
        sim.avancaNoTempo(2);
        System.out.println(sim.fornecedorComMaiorFaturacao().getNome()+"\n");
        System.out.println(sim.casaComMaiorDespesa().getTotalAPagar()+"\n");
        System.out.println(sim.consumoCasasEntreDatas(LocalDateTime.now().plusDays(1),LocalDateTime.now().plusDays(5)).keySet());
    }

    @Test
    void getFornecedores() {
    }
}