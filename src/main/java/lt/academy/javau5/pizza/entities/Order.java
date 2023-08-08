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
import lombok.NoArgsConstructor;






@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pizza_orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@JoinTable(name = "pizza_orders_pizza", joinColumns = @JoinColumn(name = "pizza_orders_id"), inverseJoinColumns = @JoinColumn(name = "pizza_id"))
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Pizza> pizzas;
	
//	Todo
//	@Column(name = "custom_pizza")
//	private List<CustomPizza> customPizza;

	@Column(name = "pizza_orders_price")
	private double price;
	
//	ToDo
//	User ManyToMany relationship

	

}
