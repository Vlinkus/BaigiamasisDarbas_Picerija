package lt.academy.javau5.pizza.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.academy.javau5.pizza.entities.Pizza;
import lt.academy.javau5.pizza.services.PizzaService;

@RestController
@RequestMapping("/api")
public class PizzaController {

	
		private PizzaService pizzaService;
		
		@Autowired
		public PizzaController(PizzaService thePizzaService) {
			pizzaService=thePizzaService;
		}
		
		//Shows all pizzas
		@GetMapping("/pizza")
		public List<Pizza> findAll(){
			return pizzaService.findAll();
		}
		
		
		//Shows pizza by ID
		@GetMapping("/pizza/{pizzaId}")
		public Pizza getPizza(@PathVariable int pizzaId) {
			Pizza thePizza = pizzaService.findById(pizzaId);
			if(thePizza==null) {throw new RuntimeException("Pica su nr: "+pizzaId+" nerasta");
				}
			return thePizza;
		}
		
		
		
		//Add pizza
		@PostMapping("/pizza")
		public Pizza addPizza(@RequestBody Pizza thePizza) {
			thePizza.setId(0);
			Pizza dbPizza=pizzaService.save(thePizza);
			return dbPizza;
		}
		
		//Delete pizza
		@DeleteMapping("/pizza/{pizzaId}")
		public String deletePizza(@PathVariable int pizzaId) {
			Pizza tempPizza=pizzaService.findById(pizzaId);
			if(tempPizza==null) {
				throw new RuntimeException("Pica su nr: "+pizzaId+" nerasta");
			}
			pizzaService.deleteById(pizzaId);
			return "Pica su nr: "+pizzaId+" ištrinta";
		}
		
		//Update pizza
		
		@PutMapping("/pizza")
		public Pizza updatePizza(@RequestBody Pizza thePizza) {
			Pizza dbPizza=pizzaService.save(thePizza);
			return dbPizza;
		}
}
