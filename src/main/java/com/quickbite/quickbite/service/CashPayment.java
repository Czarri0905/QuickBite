package main.java.com.quickbite.quickbite.service;
import main.java.com.quickbite.quickbite.model.Order;

public class CashPayment extends Payment {
    private double amountTendered;

    public CashPayment(double amountTendered) {
        this.amountTendered = amountTendered;
    }

    public double getChange(Order order) {
        double total = order.computeTotal(); // Assuming order.computeTotal() returns the total amount due for the order
        if(process(order)) {
            return amountTendered - total; // Return the change if payment is successful
        } else {
            throw new IllegalArgumentException("Insufficient amount tendered for the order total.");
        }
    }

    @Override
    public boolean process(Order order) {
        return amountTendered >= order.computeTotal(); // Payment is successful if the amount tendered is greater than or equal to the total amount due
    }

    @Override
    public String getReceiptLine(Order order) {
        // Implement logic to generate a receipt line for the cash payment
        return "Cash Payment - Amount Tendered: " + amountTendered + ", Change: " + getChange(order);
    }
}
