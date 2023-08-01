package lt.academy.javau5.pizza.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.academy.javau5.pizza.entities.Product;
import lt.academy.javau5.pizza.repositories.ProductRepository;
import lt.academy.javau5.pizza.exceptions.ProductAlreadyExistException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

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
	
	public Product save(Product theProduct) throws Exception{
		List<Product> products = productRepository.findAll();
		boolean productAlreadyExist = products.stream()
												.anyMatch(p->(p.getProductName().equals(theProduct.getProductName())));
		Product product = new Product();
		if(productAlreadyExist)
			throw new ProductAlreadyExistException("Toks produktas jau egzistuoja.");
		return productRepository.save(theProduct);
	}
	public void delete(int productId) {
		Product product = productRepository.findById(productId).orElseThrow();
		productRepository.delete(product);
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
