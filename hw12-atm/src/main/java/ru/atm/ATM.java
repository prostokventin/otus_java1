package ru.atm;


import java.util.*;

public class ATM {

    private final List<Storage> storage = new ArrayList<>();

    public ATM() {
        storage.add(new Cartridge(Banknote.FIFTY, 0));
        storage.add(new Cartridge(Banknote.ONE_HUNDRED, 0));
        storage.add(new Cartridge(Banknote.FIVE_HUNDERED, 0));
        storage.add(new Cartridge(Banknote.ONE_THOUSAND, 0));
        storage.add(new Cartridge(Banknote.FIVE_THOUSAND, 0));
    }

    public void depositCash(Cash cash) {
            for (var cartridge : storage) {
                Banknote cartridgeBanknote = cartridge.getBanknote();
                cartridge.add(cash.getBanknoteAmount(cartridgeBanknote));
            }
    }

    public Cash withdrawCash(int number) {

        int[] banknotesToTake = {0, 0, 0, 0, 0};

        for (int i = storage.size() - 1; i >= 0; i--) {

            Storage cartridge = storage.get(i);
            int banknoteValue = cartridge.getBanknote().getValue();

            if (banknoteValue > number) {
                continue;
            }
            if (number / banknoteValue > cartridge.getTotalAmount()) {
                continue;
            }

            banknotesToTake[i] = number / banknoteValue;
            number = number % banknoteValue;
        }

        if (number > 0) {
            throw new UnsupportedOperationException("Невозможно выдать запрошенную сумму");
        }

        Cash withdrawal = new Cash();
        for (int i = 0; i < storage.size(); i++) {
            Storage cartridge = storage.get(i);
            cartridge.subtract(banknotesToTake[i]);
            withdrawal.add(cartridge.getBanknote(), banknotesToTake[i]);
        }

        return withdrawal;
    }

    public int getBalance() {
        int balance = 0;
        for (var cartridge : storage) {
            balance += cartridge.getTotalAmount() * cartridge.getBanknote().getValue();
        }
        return balance;
    }

}
