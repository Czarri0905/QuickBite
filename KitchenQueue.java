import java.time.Duration;
import java.util.List;

public class KitchenQueue {
    private List<Order> orders;
    private int avgPrepMinutes;

    public KitchenQueue(List<Order> orders, int avgPrepMinutes) {
        this.orders = orders;
        this.avgPrepMinutes = avgPrepMinutes;
    }

    public void receiveOrder(Order order) {
        // Implement logic to receive an order and add it to the queue
        orders.add(order);
    }

    public Order getNextOrder() {
        // Implement logic to retrieve the next order from the queue
        if (!orders.isEmpty()) {
            return orders.remove(0);
        }
        return null;
    }

    public void markReady(Order order) {
        // Implement logic to mark an order as ready for pickup or delivery
        order.setStatus(OrderStatus.READY);
    }

    public Duration estimateOrderTime(Order order) {
        // Implement logic to estimate the preparation time for an order
        // int totalPrepTime = avgPrepMinutes * order.getItems().size();
        return null; // Placeholder for actual implementation
    }
}
