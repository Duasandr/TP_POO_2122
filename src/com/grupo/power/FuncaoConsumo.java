package com.grupo.power;

import com.grupo.house.Casa;

import java.io.Serializable;
import java.util.Locale;

public abstract class FuncaoConsumo implements Serializable {
    /**
     * Função abstrata que permite calcular o total a pagar de um consumo de uma casa
     * @return total a pagar
     */
    public abstract double calculaTotalPagar(Casa casa,FornecedorEnergia fornecedor);

    @Override
    public abstract String toString();

    public static FuncaoConsumo parse(String str){
        FuncaoConsumo funcao = null;
        switch (str.toUpperCase(Locale.ROOT)){
            case "FUNCAOPADRAO":
                funcao = new FuncaoConsumoPadrao();
                break;
            default:
                break;
        }
        return funcao;
    }
}
