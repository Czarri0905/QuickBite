package com.quickbite.model;
public class OrderItem {
    private int quantity;
    private MenuItem item;
    private String size;
    private String remarks;

    public OrderItem(MenuItem item, int quantity, String size, String remarks) {
        this.item = item;
        this.quantity = quantity;
        this.size = size;
        this.remarks = remarks;
    }

    public double getSubtotal() {
        // Implement logic to calculate the subtotal for the order item
        return item.getPrice() * quantity;
    }

    public int getQuantity() {
        return quantity;
    }
    
    public void updateQuantity(int qty) {
        // Implement logic to update the quantity of the order item
        this.quantity = qty;
    }
}
