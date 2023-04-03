package src;

public class Cart {
	public int id,qty;
	public float total,price;
	public String name,imagepath;
	
	public Cart(int id, int qty, float total,float price, String name, String imagepath) {
		this.id = id;
		this.qty = qty;
		this.total = total;
		this.name = name;
		this.imagepath = imagepath;
		this.price = price;
	}	
}
