package lt.academy.javau5.pizza.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Table(name="pizza")

public class Pizza {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	
	@Column(name="pizzaName")
	private String pizzaName;
		
	
	@Column(name="pizzaPhoto", length=1048576)
	private byte[] pizzaPhoto;
	
	@Column(name="pizzaPrice")
	private double pizzaPrice;
	
	@Column(name="pizzaSize")
	private int pizzaSize;
	
	@JoinTable(name = "product_pizza", joinColumns = @JoinColumn(name = "pizza_id"),
									   inverseJoinColumns = @JoinColumn(name = "product_id"))
	
	@ManyToMany(fetch=FetchType.LAZY,
	cascade= {CascadeType.PERSIST,
			CascadeType.MERGE,
			CascadeType.DETACH,
			CascadeType.REFRESH})
	private List<Product> products;

	
	public Pizza () {}
	
	public Pizza(String pizzaName, byte[] pizzaPhoto, double pizzaPrice, int pizzaSize) {
		
		this.pizzaName = pizzaName;
		this.pizzaPhoto = pizzaPhoto;
		this.pizzaPrice = pizzaPrice;
		this.pizzaSize = pizzaSize;
		
	}
	
//		public Pizza(int id, String pizzaName, byte[] pizzaPhoto, double pizzaPrice, int pizzaSize) {
//		
//		this.id=id;
//		this.pizzaName = pizzaName;
//		this.pizzaPhoto = pizzaPhoto;
//		this.pizzaPrice = pizzaPrice;
//		this.pizzaSize = pizzaSize;
//		
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPizzaName() {
		return pizzaName;
	}

	public void setPizzaName(String pizzaName) {
		this.pizzaName = pizzaName;
	}

	public byte[] getPizzaPhoto() {
		return pizzaPhoto;
	}

	public void setPizzaPhoto(byte[] pizzaPhoto) {
		this.pizzaPhoto = pizzaPhoto;
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

	@Override
	public String toString() {
		return "Pizza [id=" + id + ", pizzaName=" + pizzaName + 
				", pizzaPrice=" + pizzaPrice + ", pizzaSize=" + pizzaSize + ", products=" + products + "]";
	}

	public void addProduct(Product theProduct) {
		if(products==null) {
			products = new ArrayList<>();
			
		}
		products.add(theProduct);
	}
	
	
	
	//If needed
	/*
	@Column(name="isSpicy")
	private boolean isSpicy;
	
	@Column(name="hasMeat")
	private boolean hasMeat;
	
	@Column(name="soldOut")
	private boolean soldOut;
	*/
	
}
