package ru.atm;


import java.util.*;

public class ATMImpl implements ATM {

    private final List<Storage> storage = new ArrayList<>();

    public ATMImpl() {
        Arrays.stream(Banknote.values()).forEach(banknote -> storage.add(new Cartridge(banknote, 0)));
    }

    @Override
    public void depositCash(Cash cash) {
            for (var cartridge : storage) {
                Banknote cartridgeBanknote = cartridge.getBanknote();
                cartridge.add(cash.getBanknoteAmount(cartridgeBanknote));
            }
    }

    @Override
    public Cash withdrawCash(int number) {

        int[] banknotesToTake = new int[storage.size()];

        for (int i = storage.size() - 1; i >= 0; i--) {

            Storage cartridge = storage.get(i);
            int banknoteValue = cartridge.getBanknote().getValue();

            if (banknoteValue > number || number / banknoteValue > cartridge.getTotalAmount()) {
                banknotesToTake[i] = 0;
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

    @Override
    public int getBalance() {
        int balance = 0;
        for (var cartridge : storage) {
            balance += cartridge.getTotalAmount() * cartridge.getBanknote().getValue();
        }
        return balance;
    }

}
