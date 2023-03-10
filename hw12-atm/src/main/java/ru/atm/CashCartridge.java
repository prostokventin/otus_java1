package ru.atm;

public class CashCartridge {

    private Denomination denomination;
    private int amount;

    public CashCartridge(Denomination denomination, int amount) {
        this.denomination = denomination;
        this.amount = amount;
    }

    public void putBanknotes(int amount) {
        this.amount += amount;
    }

    public int withdrawBanknotes(int amount) {
        if (this.amount >= amount) {
            this.amount -= amount;
            return amount;
        }
    }
}
