package lt.academy.javau5.pizza.entities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Table (name = "products")
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="product_name", unique = true)
	private String productName;
	
	@Column(name="product_price")
	private double productPrice;
	
//	@JoinTable(name = "product_pizza") //	, joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "pizza_id"))
	@ManyToMany(fetch = FetchType.LAZY,
	cascade= {CascadeType.PERSIST, 
			CascadeType.MERGE},
	mappedBy = "products") //	, CascadeType.DETACH, CascadeType.REFRESH}) 
    @JsonIgnore
	private List<Pizza> pizzas;

		
	public Product(String productName, double productPrice) {
		
		this.productName = productName;
		this.productPrice = productPrice;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public List<Pizza> getPizza() {
		return pizzas;
	}

	public void setPizza(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", productPrice=" + productPrice + "]";
	}
	
	public void addPizza(Pizza thePizza) {
		if(pizzas ==null) {
			pizzas= new ArrayList<>();
		}
		pizzas.add(thePizza);
	}
	

}
