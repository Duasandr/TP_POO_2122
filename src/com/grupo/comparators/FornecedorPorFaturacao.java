package com.grupo.comparators;

import com.grupo.power.FornecedorEnergia;

import java.util.Comparator;

public class FornecedorPorFaturacao implements Comparator<FornecedorEnergia> {

    @Override
    public int compare(FornecedorEnergia o1, FornecedorEnergia o2) {
        DoubleDecrescente comp = new DoubleDecrescente();
        return comp.compare(o1.getFaturacao(), o2.getFaturacao());
    }
}
