package lt.academy.javau5.pizza.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import lt.academy.javau5.pizza.entities.Product;
import lt.academy.javau5.pizza.exceptions.PizzaDoesNotExistException;
import lt.academy.javau5.pizza.exceptions.ProductAlreadyExistException;
import lt.academy.javau5.pizza.exceptions.ProductDoesNotExistExecption;
import lt.academy.javau5.pizza.exceptions.ProductIsStillUsedInSomePizzaException;
import lt.academy.javau5.pizza.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	public List<Product> findAll() {
		List<Product> products = productRepository.findAll();
			return products;
	}


	public Product findById(int productId) {
		Product product = productRepository.findById(productId).orElseThrow(
				() -> new ProductDoesNotExistExecption("Product with ID: " +productId + " does  not exist"));
		return product;
	}
	
	public Product save(Product theProduct) throws ProductAlreadyExistException{
		Product product = productRepository.findProductByProductName(theProduct.getProductName());
		if(product != null)
			throw new ProductAlreadyExistException("Product with this name already exist");
		return productRepository.save(theProduct);
	}
	
	public Product update(Product product) {
		Product updatedProduct = null;
		if(product != null && product.getId() !=null) {
			updatedProduct = productRepository.save(product);
		}
		return updatedProduct;
		
	}
	
	public void delete(int productId) {
		try {
			productRepository.deleteById(productId);
		} catch (EmptyResultDataAccessException ex) {
	        throw new ProductDoesNotExistExecption("Product with ID: " + productId + " does not exist");
	    } catch (RuntimeException ex) {
	        throw new ProductIsStillUsedInSomePizzaException("Product with ID: " + productId + " is associated with other entities and cannot be deleted");
	    }
			
	}


	public boolean seedProductRepository() {
		if (productRepository.count()==0){
			Product p1 = new Product("Produktas1", 1 );
			Product p2 = new Product("Produktas2",  2);
			Product p3 = new Product("Produktas3",  3);
			Product p4 = new Product("Produktas4",  4);
			Product p5 = new Product("Produktas5",  5);
			Product p6 = new Product("Produktas6",  6);
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
		return productList.stream().anyMatch(product -> product.getProductName().equals(productName));
	}


		       
	       
	             


	
	
	


	
	

}
