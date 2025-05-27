package com.dollar.tenthelevenbank;

public class SavingsAccount extends Account{
    private double interestRate;

    public SavingsAccount() {
        // Default constructor for serialization
    }

    public SavingsAccount(int id, String holder, double balance, int pin, double interestRate) {
        super(id, holder, balance, pin);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return String.format("%s, Type: Savings, Interest Rate: %.1f%%", super.toString(), interestRate * 100);
    }
}
