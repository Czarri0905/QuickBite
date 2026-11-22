package main.java.com.quickbite.quickbite.model;
public class MenuItem {
    private int itemId;
    private String name;
    private String category;
    private double price;
    boolean available;

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAvailability(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }
}
