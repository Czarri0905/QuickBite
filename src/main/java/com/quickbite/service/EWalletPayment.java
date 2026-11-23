package com.quickbite.service;
import com.quickbite.model.Order;

public class EWalletPayment extends Payment {
    private String provider;
    private String referenceNo;
    private double balance;

    public EWalletPayment(String provider, String referenceNo, double balance) {
        this.provider = provider;
        this.referenceNo = referenceNo;
        this.balance = balance; //for class demonstration only
    }

    @Override
    public boolean process(Order order) {

        if(provider == null || referenceNo == null) {
            return false;
        }
        boolean providerAccepted = (provider.equals("Gcash") || 
            provider.equals("Maya") 
            || provider.equals("GrabPay"))
             ? true : false;
        boolean referenceProvider = ! referenceNo.trim().isEmpty();
    
        return providerAccepted && referenceProvider && balance >= order.computeTotal(); // Return true if payment is successful, false otherwise
    }
    @Override
    public String getReceiptLine(Order order) {
        // Implement logic to generate a receipt line for the e-wallet payment
        return "E-Wallet Payment - Provider: " + provider + ", Reference No: " + referenceNo;
    }
}
