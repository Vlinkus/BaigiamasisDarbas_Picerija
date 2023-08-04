package lt.academy.javau5.pizza.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import lt.academy.javau5.pizza.entities.PizzaResponseEntity;
import lt.academy.javau5.pizza.exceptions.PizzaAlreadyExistException;
import lt.academy.javau5.pizza.exceptions.PizzaDoesNotExistException;
import lt.academy.javau5.pizza.services.PizzaService;

@RestController
@RequestMapping("/api")
public class PizzaController {
	@Autowired
	private PizzaService pizzaService;
	
	// Shows all pizzas
	@GetMapping("/pizza")
	public List<Pizza> findAll() {
		return pizzaService.findAll();
	}

	// Shows pizza by ID
	@GetMapping("/pizza/{pizzaId}")
	public Pizza getPizza(@PathVariable int pizzaId) {
		return pizzaService.findById(pizzaId);
	}

	// Add pizza
	@PostMapping("/pizza")
	public PizzaResponseEntity addPizza(@RequestBody Pizza thePizza) {
				Pizza pizza = pizzaService.save(thePizza);
				if(pizza ==null)
				return new PizzaResponseEntity(pizza,HttpStatus.BAD_REQUEST, "Saving Pizza Failed");
				return new PizzaResponseEntity(pizza,HttpStatus.OK, "Pizza saved succesfully");
	}

	// Delete pizza
	@DeleteMapping("/pizza/{pizzaId}")
	public ResponseEntity<String> deletePizza(@PathVariable int pizzaId) {
		return ResponseEntity.ok(pizzaService.deletePizza(pizzaId));
	}

	// Update pizza
	@PutMapping("/pizza")
	public PizzaResponseEntity updatePizza(@RequestBody Pizza pizza) {
		Pizza savePizza = pizzaService.update(pizza);
		if(savePizza ==null)
			return new PizzaResponseEntity(pizza,HttpStatus.BAD_REQUEST, "Saving Update Failed");
			return new PizzaResponseEntity(pizza,HttpStatus.OK, "Pizza Updated Succesfully");
	}

	// Add pizza photo
	@PostMapping("/pizza/{pizzaId}/uploadPhoto")
	public ResponseEntity<String> uploadPizzaPhoto(@PathVariable int pizzaId,
			@RequestParam("pizzaPhoto") MultipartFile file) {
		try {
			if (!file.isEmpty()
					&& (file.getContentType().equals("image/jpg") || file.getContentType().equals("image/jpeg"))) {
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

}