import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderNo;
    private String orderType;
    private List<OrderItem> items = new ArrayList<>();
    private OrderStatus status;

    public Order(int orderNo, String orderType) {
        this.orderNo = orderNo;
        this.orderType = orderType;
        this.status = OrderStatus.PENDING; // Initial status
    }

    /**
     * Adds an item to the order.
     *
     * @param orderItem The MenuItem to be added to the order.
     */
    public void addItem(OrderItem orderItem) {
        if (orderItem != null) {
            items.add(orderItem);
        }
    }

    /**
     * Removes an item from the order.
     *
     * @param orderItem The MenuItem to be removed from the order.
     */
    public void removeItem(OrderItem orderItem) {
        items.remove(orderItem);
    }

    /**
     * Computes the total amount for the order by summing up the subtotals of all order items.
     *
     * @return The total amount for the order.
     */
    public double computeTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    /**
     * Advances the order status to the next stage in the order lifecycle.
     * The order progresses from PENDING -> PREPARING -> READY -> COMPLETED.
     * If the order is already completed, an IllegalStateException is thrown.
     */
    public void advanceStatus() {
        switch (status) {
        case PENDING:   status = OrderStatus.PREPARING; break;
        case PREPARING: status = OrderStatus.READY;     break;
        case READY:     status = OrderStatus.COMPLETED; break;
        default: throw new IllegalStateException("Order is already completed");
        }
    }

    /**
     * Checks out the order and generates a transaction record.
     *
     * @return A TransactionRecord containing the transaction details.
     */
    public TransactionRecord checkout() {
        return new TransactionRecord(
            "TXN" + orderNo, // Generate a transaction number based on the order number
            new java.sql.Date(System.currentTimeMillis()), // Current date and time
            computeTotal() // Total amount for the order
        );
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items); // Return a copy to prevent external modification
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
