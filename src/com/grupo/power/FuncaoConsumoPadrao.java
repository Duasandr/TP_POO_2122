package com.grupo.power;

import com.grupo.house.Casa;

import java.io.Serializable;

public class FuncaoConsumoPadrao extends FuncaoConsumo implements Serializable {

    /**
     * Calcula o total a pagar segundo os dados do fornecedor. EnergiaGasta * preço kw * 1 + imposto.
     * @param casa Dados da casa a pagar o valor.
     * @param fornecedor Dados do fornecedor.
     * @return double Total a pagar
     */
    @Override
    public double calculaTotalPagar(Casa casa , FornecedorEnergia fornecedor) {
        return casa.getConsumoEnergia() * fornecedor.getValorBase() * (1 + fornecedor.getImposto());
    }

    @Override
    public String toString() {
        return "Faturação Padrão";
    }
}
