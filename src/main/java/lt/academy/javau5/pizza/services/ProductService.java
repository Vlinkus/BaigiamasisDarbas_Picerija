package lt.academy.javau5.pizza.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.academy.javau5.pizza.entities.Product;
import lt.academy.javau5.pizza.exceptions.NullCanNotBeSavedException;
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
		return findProductByIdOrThrowException(productId);
	}
	
	public Product save(Product theProduct) {
		isProductFieldsCorrectForSaving(theProduct);
		return productRepository.save(theProduct);
	}
	
	public Product update(Product product) {
		if(product == null || product.getId() == null) 
			throw new ProductDoesNotExistExecption("Product can not be updated");
		if(product.getProductName() == null || product.getProductName() == "")
			throw new NullCanNotBeSavedException("Product can not be updated with empty name");
		findProductByIdOrThrowException(product.getId());
		return productRepository.save(product);	
	}
	
	public String delete(int productId) {
		Product product = findProductByIdOrThrowException(productId);
		if(!product.getPizzas().isEmpty())
			throw new ProductIsStillUsedInSomePizzaException("Product with ID: " + productId + " is associated with other entities and cannot be deleted");
			productRepository.delete(product);
			return "Product Deleted Succesfully";		
	}
	
	private Product findProductByIdOrThrowException(Integer productId) {
		Product product = productRepository.findById(productId).orElseThrow(
				() -> new ProductDoesNotExistExecption("Product with ID: " + productId + " does not exist"));
			return product;
	}
	
	private void isProductFieldsCorrectForSaving(Product theProduct) {
		if(theProduct == null || theProduct.getProductName() == null || theProduct.getProductName() == "")
			throw new NullCanNotBeSavedException("Empty product can not be saved");
		Product product = productRepository.findProductByProductName(theProduct.getProductName());
		if(product != null)
			throw new ProductAlreadyExistException("Product with this name already exist");
		if(theProduct.getId() != null) {
			Product productFromRepo = productRepository.findById(theProduct.getId()).orElse(null);
			if(productFromRepo != null) throw new ProductAlreadyExistException("Product with ID:" + theProduct.getId() + "id already exist");
		}
	}
}
