package lt.academy.javau5.pizza.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.academy.javau5.pizza.entities.Pizza;
import lt.academy.javau5.pizza.entities.Product;
import lt.academy.javau5.pizza.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	
	public ProductService(ProductRepository theProductRepository) {
		productRepository = theProductRepository;
	}
	
	
	public List<Product> findAll() {
			return productRepository.findAll();
	}


	public Product findById(int productId) {
		Optional<Product> result = productRepository.findById(productId);
		;
		Product theProduct = null;
		if (result.isPresent()) {
			theProduct = result.get();
		} else {
			throw new RuntimeException("Produktas su nr: " + productId + " nerastas");
		}
		return theProduct;
	}
	
	public Product save(Product theProduct) {
		return productRepository.save(theProduct);
	}
	
	public void delete(Product product) {
		productRepository.delete(product);

	}


	public boolean seedProductRepository() {
		if (productRepository.count()==0){
			Product p1 = new Product("Produktas1", null, 1);
			Product p2 = new Product("Produktas2", null, 2);
			Product p3 = new Product("Produktas3", null, 3);
			Product p4 = new Product("Produktas4", null, 4);
			Product p5 = new Product("Produktas5", null, 5);
			Product p6 = new Product("Produktas6", null, 6);
			productRepository.save(p1);
			productRepository.save(p2);
			productRepository.save(p3);
			productRepository.save(p4);
			productRepository.save(p5);
			productRepository.save(p6);
		}
		return false;
	}


	public boolean productAlreadyExists(String productName) {
		
		List<Product> productList = productRepository.findAll();
		return productList
				.stream()
				.anyMatch(product -> product
						.getProductName()
						.equals(productName));
				
	}


	
	
	


	
	

}
