public class OrderItem {
    private int quantity;
    private String size;
    private String remarks;

    public double getSubtotal() {
        // Implement logic to calculate the subtotal for the order item
        return 0;
    }

    public void updateQuantity(int qty) {
        // Implement logic to update the quantity of the order item
        this.quantity = qty;
    }
}
