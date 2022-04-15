package com.grupo.power;

@FunctionalInterface
public interface FuncaoDesconto {
    double descontoPorDia(double consumo_casa , int numero_dispositivos);
}
