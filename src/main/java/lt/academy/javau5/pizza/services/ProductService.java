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
		//Check if product with that ID exists, if exists returns product.
		return findProductByIdOrThrowException(productId);
	}
	
	public Product save(Product theProduct) {
		//Checks if required fields are correct for saving
		isProductFieldsCorrectForSaving(theProduct);
		return productRepository.save(theProduct);
	}
	
	public Product update(Product product) {
		//Checks if sent product update is not null OR product ID is not null, if it is, throw exception
		if(product == null || product.getId() == null) 
			throw new ProductDoesNotExistExecption("Product can not be updated");
		//Checks if sent product has name (not null or empty), if it is,, throw exception
		if(product.getProductName() == null || product.getProductName() == "")
			throw new NullCanNotBeSavedException("Product can not be updated with empty name");
		findProductByIdOrThrowException(product.getId());
		return productRepository.save(product);	
	}
	
	public String delete(int productId) {
		//Checks if product with this productId exist in DB if not, throw exception 
		Product product = findProductByIdOrThrowException(productId);
		//Checks if product is associated with any pizza. If it is, throw exception
		if(product.getPizzas()!= null && !product.getPizzas().isEmpty())
			throw new ProductIsStillUsedInSomePizzaException("Product with ID: " + productId + " is associated with other entities and cannot be deleted");
			productRepository.delete(product);
			return "Product Deleted Succesfully";		
	}
	
	public Product findProductByIdOrThrowException(Integer productId) {
		//Checks if product with this productId exist in DB if not, throw exception 
		Product product = productRepository.findById(productId).orElseThrow(
				() -> new ProductDoesNotExistExecption("Product with ID: " + productId + " does not exist"));
			return product;
	}
	
	public void isProductFieldsCorrectForSaving(Product theProduct) {
		if(theProduct == null || theProduct.getProductName() == null || theProduct.getProductName() == "")
			throw new NullCanNotBeSavedException("Empty product can not be saved");
		//Checks if product with this name exists in DB if name exists throw exception 
		Product product = productRepository.findProductByProductName(theProduct.getProductName());
		if(product != null)
			throw new ProductAlreadyExistException("Product with this name already exist");
		//Checks if product is sent with ID
		//If there is ID checks if that ID is already in DB.if ID exists throw exception
		if(theProduct.getId() != null) {
			Product productFromRepo = productRepository.findById(theProduct.getId()).orElse(null);
			if(productFromRepo != null) throw new ProductAlreadyExistException("Product with ID:" + theProduct.getId() + "id already exist");
		}
	}
}
