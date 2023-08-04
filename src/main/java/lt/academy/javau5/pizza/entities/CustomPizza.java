package lt.academy.javau5.pizza.entities;

import java.util.ArrayList;
import java.util.List;





public class CustomPizza {

	private static int idGenerator = 1;
	
	private int id;

	private String pizzaName;

	private double pizzaPrice;

	private int pizzaSize;

	private List<Product> products;

	public CustomPizza() {
		this.id = idGenerator;
		idGenerator++;
		this.pizzaName = "customPizza";
	}

	
	public CustomPizza(int pizzaSize, double pizzaPrice, List<Product> products ) {

		this.id = idGenerator;
		idGenerator++;
		this.pizzaName = "customPizza";
		this.pizzaSize = pizzaSize;
		this.products=products;
		this.pizzaPrice=setTotalPrice();
	}

	
		private double setTotalPrice() {
	        double totalPrice = 0;
	        if (products != null) {
	            for (Product product : products) {
	                totalPrice += product.getProductPrice();
	            }
	        }
	        return totalPrice;
		}
	


	public void addProduct(Product theProduct) {
		if (products == null) {
			products = new ArrayList<>();
		}
		products.add(theProduct);
	}


	public String getPizzaName() {
		return pizzaName;
	}


	public void setPizzaName(String pizzaName) {
		this.pizzaName = pizzaName;
	}


	public double getPizzaPrice() {
		return pizzaPrice;
	}


	public void setPizzaPrice(double pizzaPrice) {
		this.pizzaPrice = pizzaPrice;
	}


	public int getPizzaSize() {
		return pizzaSize;
	}


	public void setPizzaSize(int pizzaSize) {
		this.pizzaSize = pizzaSize;
	}


	public List<Product> getProducts() {
		return products;
	}


	public void setProducts(List<Product> products) {
		this.products = products;
	}


	public int getId() {
		return id;
	}

	
	
	
}
