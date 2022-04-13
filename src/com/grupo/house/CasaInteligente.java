package com.grupo.house;

public interface CasaInteligente {
    void ligarTodosDispositivos(String divisao);
    void desligarTodosDispositivos(String divisao);
    void ligarDispositivo(String id);
    void desligarDispositivo(String id);
}
