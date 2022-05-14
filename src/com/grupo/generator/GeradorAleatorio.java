package com.grupo.generator;

import com.grupo.device.SmartBulb;
import com.grupo.device.SmartDevice;
import com.grupo.device.SmartSpeaker;
import com.grupo.house.Casa;
import com.grupo.house.Divisao;
import com.grupo.power.FornecedorEnergia;
import com.grupo.power.FuncaoConsumoPadrao;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GeradorAleatorio {
    private Random random;
    private HashSet<FornecedorEnergia> fornecedores;
    private HashSet<Casa> casas;

    private static int id_dispositivo = 0;
    private static int id_divisao = 0;
    private static int id_casa = 0;
    private static int id_fornecedor = 0;
    private static int id_pessoa = 0;

    public GeradorAleatorio(){
        this.random = new Random(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        this.fornecedores = new HashSet<>();
        this.casas = new HashSet<>();
        geraCasas();
        geraFornecedor();
        fazContratos();
    }

    private Set<SmartDevice> geraDispositivos(){
        int n = Math.abs(random.nextInt(8) + 1);
        Set<SmartDevice> devices = new HashSet<>(n);
        for (int i = 0; i < n; i++) {
            SmartDevice.Estado estado = (i&1) == 0 ? SmartDevice.Estado.LIGADO : SmartDevice.Estado.DESLIGADO;
            SmartBulb.Tonalidade tone = (i&1) == 0 ? SmartBulb.Tonalidade.FRIA : (i%3) == 0 ? SmartBulb.Tonalidade.NEUTRA : SmartBulb.Tonalidade.QUENTE;
            SmartDevice device = (i&1) == 0 ? new SmartSpeaker("speaker" + id_dispositivo++,estado,i*2.9+6,i*12,"canal"+i,i*13+1) :
                    new SmartBulb("bulb" + id_dispositivo++,estado,i*2.9+6,tone,i*.043+0.23);
            devices.add(device);
        }
        return devices;
    }

    private Set<Divisao> geraDivisoes(){
        int n = Math.abs(random.nextInt(4) + 1);
        Set<Divisao> divisoes = new HashSet<>(n);
        for (int i = 0; i < n; i++) {
            Divisao divisao = new Divisao("Divisao"+i,geraDispositivos());
            divisoes.add(divisao);
        }
        return divisoes;
    }

    private void geraCasas(){
        int n = Math.abs(random.nextInt(20) + 1);
        HashSet<Casa> casas = new HashSet<>(n);
        for (int i = 0; i < n; i++) {
            Casa casa = new Casa("Pessoa"+id_pessoa++,"Casa"+id_casa++,1200+id_pessoa,geraDivisoes(),"null",-1);
            casas.add(casa);
        }
        this.casas = casas;
    }

    private void geraFornecedor(){
        int n = Math.abs(random.nextInt(12) + 1);
        HashSet<FornecedorEnergia> fornecedores = new HashSet<>(n);
        for (int i = 0; i < n; i++) {
            FornecedorEnergia fornecedor = new FornecedorEnergia("Fornecedor" + id_fornecedor++,i*0.25+0.13,0.13, new FuncaoConsumoPadrao());
            fornecedores.add(fornecedor);
        }
        this.fornecedores = fornecedores;
    }

    private void fazContratos(){
        FornecedorEnergia[] pool = this.fornecedores.toArray(FornecedorEnergia[]::new);
        for (Casa casa : this.casas) {
            casa.setFornecedor(pool[Math.abs(random.nextInt() % pool.length)].getNome());
        }
    }

    public Set<Casa> getCasas() {
        Set<Casa> copia = new HashSet<>();
        for (Casa casa : this.casas) {
            copia.add(casa.clone());
        }
        return copia;
    }

    public Set<FornecedorEnergia> getFornecedores() {
        Set<FornecedorEnergia> copia = new HashSet<>();
        for (FornecedorEnergia fornecedor : this.fornecedores) {
            copia.add(fornecedor.clone());
        }
        return copia;
    }
}
