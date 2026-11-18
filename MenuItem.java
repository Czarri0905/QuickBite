public class MenuItem {
    private int itemId;
    private String name;
    private String category;
    private double price;
    boolean available;

    public void setPrice(double price) {
        this.price = price;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

    public boolean isAvailable() {
        return available;
    }
}
