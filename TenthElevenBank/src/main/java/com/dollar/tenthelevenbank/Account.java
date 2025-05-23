package com.dollar.tenthelevenbank;

public class Account {
    protected int id;
    protected String holder;
    protected double balance;
    protected int pin;

    public Account(int id, String holder, double balance, int pin) {
        this.id = id;
        this.holder = holder;
        this.balance = balance;
        this.pin = pin;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public String getHolder() {
        return holder;
    }

    public double getBalance() {
        return balance;
    }

    public int getPin() {
        return pin;
    }

    @Override
    public String toString() {
        return String.format("Account Number: %d\nAccount Holder: %s\nBalance: R%.2f", id, holder, balance);
    }
}
