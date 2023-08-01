package lt.academy.javau5.pizza.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.academy.javau5.pizza.entities.Pizza;
import lt.academy.javau5.pizza.entities.Product;
import lt.academy.javau5.pizza.repositories.PizzaRepository;

@Service
public class PizzaService {
	
	@Autowired
	private PizzaRepository pizzaRepository;

	
//	public PizzaService(PizzaRepository thePizzaRepository) {
//		pizzaRepository = thePizzaRepository;
//	}

	public List<Pizza> findAll() {
		return pizzaRepository.findAll();
	}

	public Pizza findById(int pizzaId) {
		Optional<Pizza> result = pizzaRepository.findById(pizzaId);
		;
		Pizza thePizza = null;
		if (result.isPresent()) {
			thePizza = result.get();
		} else {
			throw new RuntimeException("Pica su nr: " + pizzaId + " nerasta");
		}
		return thePizza;
	}

	public Pizza save(Pizza thePizza) {
		return pizzaRepository.save(thePizza);
	}

	public void deletePizza(Pizza pizza) {
		pizzaRepository.delete(pizza);
	}
	
	

	public void uploadPizzaPhoto(int pizzaId, byte[] photoBytes) {
		Pizza pizza = pizzaRepository.findById(pizzaId).orElse(null);
		if (pizza != null) {
			pizza.setPizzaPhoto(photoBytes);
			pizzaRepository.save(pizza);
		} else {
			throw new RuntimeException("Pica su " + pizzaId + " nerasta.");
		}
	}
	
	
//	public boolean seedPizzaRepository() {
//		if (pizzaRepository.count()==0){
//			Pizza p1 = new Pizza("Pica1", null, 1, 10, null);
//			Pizza p2 = new Pizza("Pica2", null, 2, 20, null);
//			Pizza p3 = new Pizza("Pica3", null, 3, 30, null);
//			Pizza p4 = new Pizza("Pica4", null, 4, 40, null);
//			Pizza p5 = new Pizza("Pica5", null, 5, 50, null);
//			Pizza p6 = new Pizza("Pica6", null, 6, 60, null);
//			pizzaRepository.save(p1);
//			pizzaRepository.save(p2);
//			pizzaRepository.save(p3);
//			pizzaRepository.save(p4);
//			pizzaRepository.save(p5);
//			pizzaRepository.save(p6);
//		}
//		return false;
//	}

}
