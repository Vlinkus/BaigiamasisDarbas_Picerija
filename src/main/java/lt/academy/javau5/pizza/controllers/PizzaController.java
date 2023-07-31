package lt.academy.javau5.pizza.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lt.academy.javau5.pizza.entities.Pizza;

import lt.academy.javau5.pizza.services.PizzaService;


@RestController
@RequestMapping("/api")
public class PizzaController {

	private PizzaService pizzaService;

	@Autowired
	public PizzaController(PizzaService thePizzaService) {
		pizzaService = thePizzaService;
	}

	// Shows all pizzas
	@GetMapping("/pizza")
	public List<Pizza> findAll() {
		return pizzaService.findAll();
	}

	// Shows pizza by ID
	@GetMapping("/pizza/{pizzaId}")
	public Pizza getPizza(@PathVariable int pizzaId) {
		Pizza thePizza = pizzaService.findById(pizzaId);
		if (thePizza == null) {
			throw new RuntimeException("Pica su nr: " + pizzaId + " nerasta");
		}
		return thePizza;
	}

	// Add pizza
	@PostMapping("/pizza")
	public Pizza addPizza(@RequestBody Pizza thePizza) {
		thePizza.setId(0);
		Pizza dbPizza = pizzaService.save(thePizza);
		return dbPizza;
	}

	// Delete pizza
	@DeleteMapping("/pizza/{pizzaId}")
	public String deletePizza(@PathVariable int pizzaId) {
		Pizza tempPizza = pizzaService.findById(pizzaId);
		if (tempPizza != null) {
			pizzaService.deletePizza(tempPizza);

		} else {
			throw new RuntimeException("Pica su nr: " + pizzaId + " nerasta");
		}

		return "Pica su nr: " + pizzaId + " ištrinta";
	}

	// Update pizza

	@PutMapping("/pizza")
	public Pizza updatePizza(@RequestBody Pizza thePizza) {
		Pizza dbPizza = pizzaService.save(thePizza);
		return dbPizza;
	}

	// Add pizza photo
	@PostMapping("/pizza/{pizzaId}/uploadPhoto")
	public ResponseEntity<String> uploadPizzaPhoto(@PathVariable int pizzaId,
			@RequestParam("pizzaPhoto") MultipartFile file) {
		try {
			if (!file.isEmpty()
					&& (file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg"))) {
				byte[] photoBytes = file.getBytes();
				pizzaService.uploadPizzaPhoto(pizzaId, photoBytes);
				return ResponseEntity.ok("Nuotrauka sėkmingai įkelta!");
			} else {
				return ResponseEntity.badRequest().body("Prašome įkelti PNG arba JPEG formato nuotrauką.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Įvyko klaida įkeliant nuotrauką.");
		}
	}

	//If needed
	
	@GetMapping("/pizza/{pizzaId}/photo")
    public ResponseEntity<byte[]> getPizzaPhotoById(@PathVariable int pizzaId) {
        Pizza pizza = pizzaService.findById(pizzaId);
        if (pizza == null || pizza.getPizzaPhoto() == null) {
            return ResponseEntity.notFound().build();
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); 
            headers.setContentLength(pizza.getPizzaPhoto().length);
            return new ResponseEntity<>(pizza.getPizzaPhoto(), headers, HttpStatus.OK);
        }
    }
	
	
	
	
//	@GetMapping("/dummyPizza")
//	public void addDummy() {
//		Pizza p = new Pizza("TESTAVIMAS", null, 10, 20, null);
//		Product prod = new Product("Agurkėliai", p);
//		List<Product> testProd = new ArrayList<>();
//		testProd.add(prod);
//		p.setProducts(testProd);
//		pizzaService.save(p);
//	}
	
	@GetMapping("/dummyPizza")
	public void addPizzaDummies() {
		pizzaService.seedPizzaRepository();
		
	}

}