package ru.atm;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM(0,0,0,0,0);
        atm.depositCash(Map.of(Denomination.FIFTY, 2, Denomination.FIVE_THOUSAND,1));

    }
}
