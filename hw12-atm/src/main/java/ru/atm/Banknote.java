package ru.atm;

public enum Banknote {

    FIFTY(50), ONE_HUNDRED(100), FIVE_HUNDERED(500), ONE_THOUSAND(1000), FIVE_THOUSAND(5000);

    private int value;

    Banknote(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}