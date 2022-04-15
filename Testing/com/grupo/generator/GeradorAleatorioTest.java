package com.grupo.generator;

import com.grupo.simulator.Simulador;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class GeradorAleatorioTest {

    @Test
    void getCasas() {
        GeradorAleatorio gerador = new GeradorAleatorio();
        Simulador sim = new Simulador(gerador.getFornecedores(),gerador.getCasas());
        sim.avancaNoTempo(2);
        System.out.println(sim.fornecedorComMaiorFaturacao()+"\n");
        System.out.println(sim.casaComMaiorDespesa()+"\n");
        System.out.println(sim.consumoCasasEntreDatas(LocalDateTime.now().plusDays(1),LocalDateTime.now().plusDays(5)));
    }

    @Test
    void getFornecedores() {
    }
}