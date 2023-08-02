package lt.academy.javau5.pizza.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.academy.javau5.pizza.entities.Pizza;
import lt.academy.javau5.pizza.entities.Product;
import lt.academy.javau5.pizza.exceptions.NullCanNotBeSavedException;
import lt.academy.javau5.pizza.exceptions.PizzaAlreadyExistException;
import lt.academy.javau5.pizza.exceptions.PizzaDoesNotExistException;
import lt.academy.javau5.pizza.repositories.PizzaRepository;
import lt.academy.javau5.pizza.repositories.ProductRepository;

@Service
public class PizzaService {

	@Autowired
	private PizzaRepository pizzaRepository;	
	@Autowired
	private ProductRepository productRepository;

	public PizzaService(PizzaRepository thePizzaRepository) {
		pizzaRepository = thePizzaRepository;
	}

	public List<Pizza> findAll() {
		return pizzaRepository.findAll();
	}

	public Pizza findById(int pizzaId) {
		Pizza pizza = pizzaRepository.findById(pizzaId)
				.orElseThrow(() -> new PizzaDoesNotExistException("Pizza with ID: " + pizzaId + " does  not exist"));
		return pizza;
	}

	public Pizza save(Pizza thePizza) throws PizzaAlreadyExistException, NullCanNotBeSavedException {
		if (thePizza == null) {
			throw new NullCanNotBeSavedException("Empty pizza can not be saved");
		} else {
			Pizza pizza = pizzaRepository.findPizzaByPizzaName(thePizza.getPizzaName()).orElse(null);
			if (pizza != null)
				throw new PizzaAlreadyExistException("Pizza with name: " + thePizza.getPizzaName() + " already exists");
			if (thePizza.getId() != null) {
				pizza = pizzaRepository.findById(thePizza.getId()).orElse(null);
				if (pizza != null)
					throw new PizzaAlreadyExistException("Pizza with ID: " + thePizza.getId() + " already exists");
			}
			List<Product> products = productRepository.findAll();
			List<Product> productsInPizza = new ArrayList<>();
			thePizza.getProducts().stream().forEach(p -> {
				Product productFromDB = products.stream().filter(pro -> pro.getProductName().equals(p.getProductName()))
						.findFirst().orElse(null);
				if (productFromDB != null)
					productsInPizza.add(productFromDB);
				else
					productsInPizza.add(productRepository.save(p));
			});
			thePizza.setProducts(productsInPizza);
			return pizzaRepository.save(thePizza);
		}
	}	

	public Pizza update(Pizza pizza) {
		if (pizza != null && pizza.getId() == null)
			throw new PizzaDoesNotExistException("Pizza with ID: " + pizza.getId() + " can not be updated");
		pizzaRepository.findById(pizza.getId()).orElseThrow(
				() -> new PizzaDoesNotExistException("Pizza with ID: " + pizza.getId() + " does  not exist"));
		
		return pizzaRepository.save(pizza);
	}

	public String deletePizza(int pizzaId) {
		Pizza pizza = pizzaRepository.findById(pizzaId)
				.orElseThrow(() -> new PizzaDoesNotExistException("Pizza with ID: " + pizzaId + " does  not exist"));
		pizzaRepository.delete(pizza);
		return "Pizza Deleted Succesfully";
	}

	public void uploadPizzaPhoto(int pizzaId, byte[] photoBytes) {
		Pizza pizza = pizzaRepository.findById(pizzaId)
				.orElseThrow(() -> new PizzaDoesNotExistException("Pizza with ID: " + pizzaId + " does  not exist"));
		pizza.setPizzaPhoto(photoBytes);
		pizzaRepository.save(pizza);
	}
	
}
