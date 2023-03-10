package ru.atm;


import java.util.*;

public class ATM {

    private final Map<Denomination, Integer> cashCartridges = new HashMap<>();
    private final List<CashCartridge> cashCartridgeList = new ArrayList<>();

//    public ATM() {
//        cashCartridgeList.add(new CashCartridge(Denomination.FIFTY, 0));
//        cashCartridgeList.add(new CashCartridge(Denomination.ONE_HUNDRED, 0));
//        cashCartridgeList.add(new CashCartridge(Denomination.FIVE_HUNDERED, 0));
//        cashCartridgeList.add(new CashCartridge(Denomination.ONE_THOUSAND, 0));
//        cashCartridgeList.add(new CashCartridge(Denomination.FIVE_THOUSAND, 0));
//    }

    public ATM(int amount50, int amount100, int amount500, int amount1000, int amount5000) {

//        this.cashCartridges.put(Denomination.FIFTY, amount50);
//        this.cashCartridges.put(Denomination.ONE_HUNDRED, amount100);
//        this.cashCartridges.put(Denomination.FIVE_HUNDERED, amount500);
//        this.cashCartridges.put(Denomination.ONE_THOUSAND, amount1000);
//        this.cashCartridges.put(Denomination.FIVE_THOUSAND, amount5000);

        cashCartridgeList.add(new CashCartridge(Denomination.FIFTY, amount50));
        cashCartridgeList.add(new CashCartridge(Denomination.ONE_HUNDRED, amount100));
        cashCartridgeList.add(new CashCartridge(Denomination.FIVE_HUNDERED, amount500));
        cashCartridgeList.add(new CashCartridge(Denomination.ONE_THOUSAND, amount1000));
        cashCartridgeList.add(new CashCartridge(Denomination.FIVE_THOUSAND, amount5000));
    }

    public void depositCash(Map<Denomination, Integer> cash) {
        for (var entry : cash.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
            cashCartridges.put(entry.getKey(), cashCartridges.get(entry.getKey()) + entry.getValue());
        }
    }

    public void getCash() {

    }

}
