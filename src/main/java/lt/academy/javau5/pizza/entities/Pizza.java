package lt.academy.javau5.pizza.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="pizza")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pizza {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	
	@Column(name="pizzaName")
	private String pizzaName;
		
	
	@Column(name="pizzaPhoto", length=1048576)
	
	private byte[] pizzaPhoto;
	
	@Column(name="pizzaPrice")
	private double pizzaPrice;
	
	@Column(name="pizzaSize")
	private int pizzaSize;
	
	@OneToMany(mappedBy = "pizza", fetch=FetchType.LAZY, cascade= {CascadeType.PERSIST,CascadeType.MERGE,
			CascadeType.DETACH, CascadeType.REFRESH})
    @JsonIgnore
    private List<Product> products;

	
	public Pizza(String pizzaName, byte[] pizzaPhoto, double pizzaPrice, int pizzaSize, List<Product> products) {
		
		this.pizzaName = pizzaName;
		this.pizzaPhoto = pizzaPhoto;
		this.pizzaPrice = pizzaPrice;
		this.pizzaSize = pizzaSize;
		this.products = products;
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
