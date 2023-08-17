package lt.academy.javau5.pizza.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import lt.academy.javau5.pizza.entities.Product;
import lt.academy.javau5.pizza.exceptions.ProductAlreadyExistException;
import lt.academy.javau5.pizza.services.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService productService;

	// Show all products
	@GetMapping("/products")
	public List<Product> findAll() {
		return productService.findAll();
	}

	// Show product by ID
	@GetMapping("/product/{productId}")
	public Product getproduct(@PathVariable int productId) {
		return productService.findById(productId);
	}

	// Add product
	@PostMapping("/product")
	public ResponseEntity<String> addProduct(@RequestBody Product product) {
		try {
			productService.save(product);
			return ResponseEntity.ok("Product added successfully.");
		} catch (ProductAlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already exists.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the request.");
		}
	}

	// Delete product
	@DeleteMapping("/product/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable int productId) {
		return ResponseEntity.ok(productService.delete(productId));
	}

	// Update product
	@PutMapping("/product")
	public ResponseEntity<String> updateProduct(@RequestBody Product product) {
			productService.update(product);
			return ResponseEntity.ok("Product updated successfully.");
	}
}
