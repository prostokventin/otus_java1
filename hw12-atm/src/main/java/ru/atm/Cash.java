package ru.atm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cash {

    private final Map<Banknote, Integer> cash = new HashMap<>();

    public Cash() {
        this.cash.put(Banknote.FIFTY, 0);
        this.cash.put(Banknote.ONE_HUNDRED, 0);
        this.cash.put(Banknote.FIVE_HUNDERED, 0);
        this.cash.put(Banknote.ONE_THOUSAND, 0);
        this.cash.put(Banknote.FIVE_THOUSAND, 0);
    }

    public Cash addFifty(int amount) {
        add(Banknote.FIFTY, amount);
        return this;
    }

    public Cash addOneHundred(int amount) {
        add(Banknote.ONE_HUNDRED, amount);
        return this;
    }

    public Cash addFiveHundred(int amount) {
        add(Banknote.FIVE_HUNDERED, amount);
        return this;
    }

    public Cash addOneThousand(int amount) {
        add(Banknote.ONE_THOUSAND, amount);
        return this;
    }

    public Cash addFiveThousand(int amount) {
        add(Banknote.FIVE_THOUSAND, amount);
        return this;
    }

    public Cash add(Banknote banknote, int amount) {
        this.cash.put(banknote, this.cash.get(banknote) + amount);
        return this;
    }

    public int getBanknoteAmount(Banknote banknote) {
        return this.cash.get(banknote);
    }

    public int getNumber() {
        return cash.entrySet().stream().mapToInt(entry -> entry.getKey().getValue() * entry.getValue()).sum();
    }


}
