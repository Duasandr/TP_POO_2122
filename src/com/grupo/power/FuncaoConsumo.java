package com.grupo.power;

import com.grupo.house.Casa;

public abstract class FuncaoConsumo {
    /**
     * Função abstrata que permite calcular o total a pagar de um consumo de uma casa
     * @return total a pagar
     */
    public abstract double calculaTotalPagar(Casa casa,FornecedorEnergia fornecedor);

    @Override
    public abstract String toString();
}
