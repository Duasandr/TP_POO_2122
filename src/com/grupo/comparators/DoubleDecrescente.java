package com.grupo.comparators;

import java.util.Comparator;

public class DoubleDecrescente implements Comparator<Double> {
    @Override
    public int compare(Double o1, Double o2) {
        int r = 0;
        if(o1.compareTo(o2) > 0) r = -1;
        else if(o1.compareTo(o2) < 0) r = 1;

        return r;
    }
}
