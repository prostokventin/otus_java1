package ru.atm;

public interface Storage {

    public void add(int amount);
    public int subtract(int amount);
    public Banknote getBanknote();
    public int getTotalAmount();
}
