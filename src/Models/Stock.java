package Models;

public class Stock {


	private String id;
	private String name;
	private double price;
	
	public Stock(String id, String name, double price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	public String getId() {
		return id;
	}
	
	public String getStockName() {
		return name;
	}
	
	public double getPrice() {
		return price;
	}
	
	@Override
	public String toString() {
		return("Stock name: " + name + ", Stock price: " + price);
	}
	
	
}
