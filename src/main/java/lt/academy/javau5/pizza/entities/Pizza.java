package lt.academy.javau5.pizza.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="pizza")
public class Pizza {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="pizzaName")
	private String pizzaName;
		
	
	@Column(name="pizzaPhoto", length=1048576)
	private byte[] pizzaPhoto;
	
	@Column(name="pizzaPrice")
	private Double pizzaPrice;
	
	@Column(name="pizzaSize")
	private Integer pizzaSize;
	
	@JoinTable(name = "pizza_products", 
				joinColumns = @JoinColumn(name = "pizza_id"),
				inverseJoinColumns = @JoinColumn(name = "product_id"))
	@ManyToMany(fetch=FetchType.LAZY,
	cascade= {CascadeType.PERSIST,
			CascadeType.MERGE})
	private List<Product> products;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY,
	cascade = { CascadeType.PERSIST, CascadeType.MERGE },
	mappedBy = "pizzas")
	private List<Order> orders;

	public Pizza(String pizzaName, byte[] pizzaPhoto, double pizzaPrice, Integer pizzaSize, List<Product> products) {		
		this.pizzaName = pizzaName;
		this.pizzaPhoto = pizzaPhoto;
		this.pizzaPrice = pizzaPrice;
		this.pizzaSize = pizzaSize;
		this.products = products;
	}

	public void addProduct(Product theProduct) {
		if(products==null) {
			products = new ArrayList<>();	
		}
		products.add(theProduct);
	}

	public Pizza(String pizzaName, byte[] pizzaPhoto, Double pizzaPrice, Integer pizzaSize, List<Product> products,
			List<Order> orders) {
		super();
		this.pizzaName = pizzaName;
		this.pizzaPhoto = pizzaPhoto;
		this.pizzaPrice = pizzaPrice;
		this.pizzaSize = pizzaSize;
		this.products = products;
		this.orders = orders;
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
