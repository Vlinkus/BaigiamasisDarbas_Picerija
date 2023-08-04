package lt.academy.javau5.pizza.services;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import lt.academy.javau5.pizza.entities.Product;
import lt.academy.javau5.pizza.exceptions.ProductAlreadyExistException;
import lt.academy.javau5.pizza.exceptions.ProductDoesNotExistExecption;
import lt.academy.javau5.pizza.exceptions.ProductIsStillUsedInSomePizzaException;
import lt.academy.javau5.pizza.repositories.ProductRepository;

@Service
public class ProductService {
	
	
	 @Autowired
	    private MessageSource messageSource;

	    public String getLocalizedText(String key, Locale locale) {
	        return messageSource.getMessage(key, null, locale);
	    }
	
	
	
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
		theProduct.getId();
		return productRepository.save(theProduct);
	}
	
	public Product update(Product product) {
		Product aproduct = productRepository.findById(product.getId()).orElseThrow(
				() -> new ProductDoesNotExistExecption("Product with ID: " + product.getId() + " does not exist"));
		Product updatedProduct = null;
		if(product != null && product.getId() !=null) {
			updatedProduct = productRepository.save(product);
		}
		return updatedProduct;
		
	}
	
	public String delete(int productId) {
		Product product = productRepository.findById(productId).orElseThrow(
				() -> new ProductDoesNotExistExecption("Product with ID: " + productId + " does not exist"));
		try {
			productRepository.delete(product);
			return "Product Deleted Succesfully";
		} catch (RuntimeException ex) {
	        throw new ProductIsStillUsedInSomePizzaException("Product with ID: " + productId + " is associated with other entities and cannot be deleted");
	    }			
	}
}
