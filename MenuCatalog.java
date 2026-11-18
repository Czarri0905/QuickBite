import java.util.ArrayList;
import java.util.List;

public class MenuCatalog {
    private List<MenuItem> items = new ArrayList<>();

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public MenuItem getItem(int id) {
        for (MenuItem item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public void updatePrice(int id, double newPrice) {
        MenuItem item = getItem(id);
        if (item != null) {
            item.setPrice(newPrice);
        }
    }
}
