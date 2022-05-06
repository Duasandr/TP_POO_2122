package com.grupo.simulator;

import com.grupo.device.SmartDevice;
import com.grupo.device.SmartSpeaker;
import com.grupo.house.Casa;
import com.grupo.house.Divisao;
import com.grupo.power.FornecedorEnergia;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

class SimuladorTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void avancaNoTempo() {
        /*SmartDevice device1 = new SmartSpeaker("dispositivo1", SmartDevice.Estado.LIGADO,120,22.99,10,"Canal1",50);
        SmartDevice device2 = new SmartSpeaker("dispositivo2",SmartDevice.Estado.LIGADO,120,22.99,10,"Canal1",50);
        SmartDevice device3 = new SmartSpeaker("dispositivo3",SmartDevice.Estado.LIGADO,120,22.99,10,"Canal1",50);
        SmartDevice device4 = new SmartSpeaker("dispositivo4",SmartDevice.Estado.LIGADO,120,22.99,10,"Canal1",50);
        SmartDevice device5 = new SmartSpeaker("dispositivo5",SmartDevice.Estado.LIGADO,120,22.99,10,"Canal1",50);
        SmartDevice device6 = new SmartSpeaker("dispositivo6",SmartDevice.Estado.LIGADO,120,22.99,10,"Canal1",50);
        SmartDevice device7 = new SmartSpeaker("dispositivo7",SmartDevice.Estado.LIGADO,120,22.99,10,"Canal1",50);
        SmartDevice device8 = new SmartSpeaker("dispositivo8",SmartDevice.Estado.LIGADO,120,22.99,10,"Canal1",50);
        SmartDevice device9 = new SmartSpeaker("dispositivo9",SmartDevice.Estado.LIGADO,120,22.99,10,"Canal1",50);
        SmartDevice device10 = new SmartSpeaker("dispositivo10",SmartDevice.Estado.LIGADO,120,22.99,10,"Canal1",50);
        SmartDevice device11 = new SmartSpeaker("dispositivo11",SmartDevice.Estado.LIGADO,120,22.99,10,"Canal1",50);
        Set<SmartDevice> devices = new HashSet<>();
        devices.add(device1);
        devices.add(device2);
        devices.add(device3);
        devices.add(device4);
        devices.add(device5);
        devices.add(device6);
        devices.add(device7);
        devices.add(device8);
        devices.add(device9);
        devices.add(device10);
        devices.add(device11);
        Divisao cozinha = new Divisao("Cozinha",devices);
        Set<Divisao> divisoes = new HashSet<>();
        divisoes.add(cozinha);
        FornecedorEnergia fornecedor = new FornecedorEnergia("edp",0.13,0.6,(a, b) -> a > 10 ? 0.95 : 0.75);
        Casa morada = new Casa("sandro","asd" , 12345,divisoes,"edp");
        Casa casa2 = morada.clone();
        casa2.setMorada("hello");
        casa2.setProprietario("sara");
        casa2.desligarTodosDispositivos("Cozinha");
        casa2.ligarTodosDispositivos("Cozinha");
        Collection<FornecedorEnergia> f = new HashSet<>();
        Collection<Casa> c = new HashSet<>();
        f.add(fornecedor);
        c.add(morada);
        c.add(casa2);
        Simulador sim = new Simulador(f,c);
        sim.avancaNoTempo(2);
        sim.avancaNoTempo(2);
        sim.avancaNoTempo(2);
        //sim.desligaDispositivo("hello","dispositivo1");
        sim.avancaNoTempo(2);
        sim.avancaNoTempo(2);
        sim.avancaNoTempo(2);
        sim.avancaNoTempo(2);
        System.out.println(sim.consumoCasasEntreDatas(LocalDateTime.now(),LocalDateTime.MAX));*/
    }

    @Test
    void emiteFaturas() {

    }
}