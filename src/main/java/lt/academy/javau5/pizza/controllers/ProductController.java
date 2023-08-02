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
		Product theProduct = productService.findById(productId);
		if (theProduct == null) {
			throw new RuntimeException("Produktas su nr: " + productId + " nerastas");
		}
		return theProduct;
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
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
	    }
	}

	// Delete product
	@DeleteMapping("/product/{productId}")
	public String deleteProduct(@PathVariable int productId) {
			productService.delete(productId);
			return "Produktas su nr: " + productId + " ištrinta";
	}

	// Update product
	@PutMapping("/product")
	public ResponseEntity<String> updateProduct(@RequestBody Product product) {
		 try {
		        productService.update(product);
		        return ResponseEntity.ok("Product updated successfully.");
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
		    }
	}

	@GetMapping("/dummyProduct")
	public void addProductDummies() {
		productService.seedProductRepository();

	}

}
