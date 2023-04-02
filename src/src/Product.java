package src;

public class Product {
	public Integer id;
	public String name,imagepath,description;
	public Float price;
	public Product(Integer id,String name, String imagepath, String description, Float price) {
		this.id = id;
		this.name = name;
		this.imagepath = imagepath;
		this.description = description;
		this.price = price;
	}
	
}
