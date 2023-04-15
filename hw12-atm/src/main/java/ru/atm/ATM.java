package ru.atm;

public interface ATM {
    public void depositCash(Cash cash);
    public Cash withdrawCash(int number);
    public int getBalance();
}
