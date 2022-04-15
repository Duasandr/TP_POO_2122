package com.grupo.comparators;

import com.grupo.power.Fatura;

import java.util.Comparator;

public class FaturaPorId implements Comparator<Fatura> {

    @Override
    public int compare(Fatura fatura1, Fatura fatura2) {
        return Long.compare(fatura1.getId(), fatura2.getId());
    }
}
