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

	public List<Pizza> findAll() {
		return pizzaRepository.findAll();
	}

	public Pizza findById(int pizzaId) {
		Pizza pizza = findPizzaByIdOrThrowException(pizzaId);
		return pizza;
	}

	public Pizza save(Pizza thePizza) throws PizzaAlreadyExistException, NullCanNotBeSavedException {
			isPizzaFieldsCorrectForSaving(thePizza);
			List<Product> productsInPizza = checkIfAllProductsInPizzaAreSavedInDataBase(thePizza);	
			thePizza.setProducts(productsInPizza);
			return pizzaRepository.save(thePizza);
	}	

	public Pizza update(Pizza pizza) {
		if (pizza == null || pizza.getId() == null)
			throw new PizzaDoesNotExistException("Pizza can not be updated");
		if (pizza.getPizzaName() == null || pizza.getPizzaName() == "") {
			throw new NullCanNotBeSavedException("Pizza can not be updated if name is empty");
		}
		findPizzaByIdOrThrowException(pizza.getId());
		return pizzaRepository.save(pizza);
	}

	public String deletePizza(int pizzaId) {
		Pizza pizza = findPizzaByIdOrThrowException(pizzaId);
		pizzaRepository.delete(pizza);
		return "Pizza Deleted Succesfully";
	}

	public void uploadPizzaPhoto(int pizzaId, byte[] photoBytes) {
		Pizza pizza = pizzaRepository.findById(pizzaId)
				.orElseThrow(() -> new PizzaDoesNotExistException("Pizza with ID: " + pizzaId + " does  not exist"));
		pizza.setPizzaPhoto(photoBytes);
		pizzaRepository.save(pizza);
	}
	
	//Methods
	
	public List<Product> checkIfAllProductsInPizzaAreSavedInDataBase(Pizza piza) {
		List<Product> products = productRepository.findAll();
		List<Product> productsInPizza = new ArrayList<>();
		if (piza.getProducts() != null) {
			piza.getProducts().stream().forEach(p -> {
				Product productFromDB = products.stream().filter(pro -> pro.getProductName().equals(p.getProductName()))
						.findFirst().orElse(null);
				if (productFromDB != null)
					productsInPizza.add(productFromDB);
				else { if(p.getProductPrice() == null) p.setProductPrice(2.5);
					productsInPizza.add(productRepository.save(p));
				}
			});
		}
		return productsInPizza;
	}
	
	private void isPizzaFieldsCorrectForSaving(Pizza thePizza) {
		if (thePizza == null || thePizza.getPizzaName() == null || thePizza.getPizzaName() == "") {
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
			}
	}
	
	private Pizza findPizzaByIdOrThrowException(Integer pizzaId) {
		Pizza pizza = pizzaRepository.findById(pizzaId)
				.orElseThrow(() -> new PizzaDoesNotExistException("Pizza with ID: " + pizzaId + " does  not exist"));
		return pizza;
	}
	
}
