package lt.academy.javau5.pizza.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
		
	//Place holder for photo file type in sql. name atm.
	@Column(name="pizzaPhoto")
	private String pizzaPhoto;
	
	@Column(name="pizzaPrice")
	private double pizzaPrice;
	
	@Column(name="pizzaSize")
	private int pizzaSize;
	
	@Column(name="pizzaDescription")
	@OneToMany
	private List<Product> pizzaDescription;

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
