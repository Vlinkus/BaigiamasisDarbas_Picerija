package lt.academy.javau5.pizza.services;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import lt.academy.javau5.pizza.entities.Pizza;
import lt.academy.javau5.pizza.repositories.PizzaRepository;

@Service
public class PizzaService {

	private PizzaRepository pizzaRepository;

	@Autowired
	public PizzaService(PizzaRepository thePizzaRepository) {
		pizzaRepository = thePizzaRepository;
	}

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

	public void deleteById(int pizzaId) {
		pizzaRepository.deleteById(pizzaId);

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

}
