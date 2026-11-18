public class RestaurantRecord
{
    private String itemname;
    private double price;
    private int preptime;

    public RestaurantRecord(String itemname, double price, int preptime)
    {
        this.itemname = itemname;
        this.price = price;
        this.preptime = preptime;
    }

    public String getItemName()
    {
        return itemname;
    }

    public double getPrice()
    {
        return price;
    }

    public int getPrepTime()
    {
        return preptime;
    }
}