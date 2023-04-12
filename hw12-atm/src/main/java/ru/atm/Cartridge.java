package ru.atm;

public class Cartridge implements Storage {

    private Banknote banknote;
    private int amount;

    public Cartridge(Banknote banknote, int amount) {
        this.banknote = banknote;
        this.amount = amount;
    }

    @Override
    public int getTotalAmount() {
        return amount;
    }

    @Override
    public void add(int amount) {
        if (amount >= 0) {
            this.amount += amount;
        }
        else {
            throw new UnsupportedOperationException("add operation error. Parameter amount = " + amount);
        }
    }

    @Override
    public int subtract(int amount) throws UnsupportedOperationException {
        if (this.amount >= amount) {
            this.amount -= amount;
        }
        else {
            throw new UnsupportedOperationException("subtract operation error. Parameter amount = " + amount);
        }
        return amount;
    }

    @Override
    public Banknote getBanknote() {
        return this.banknote;
    }


}
