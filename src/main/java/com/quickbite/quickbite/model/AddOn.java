package main.java.com.quickbite.quickbite.model;
public class AddOn {
	private String name;
	private double price;

	public AddOn() {
	}

	public AddOn(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
