package lt.academy.javau5.pizza.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import lt.academy.javau5.pizza.entities.Pizza;
import lt.academy.javau5.pizza.entities.Product;
import lt.academy.javau5.pizza.repositories.ProductRepository;

@SpringBootTest
public class ProductServiceTests {

	@Mock
	ProductRepository productRepository;

	@InjectMocks
	ProductService service;

	@Test
	void testReturnTrueIfProductServiceNotNull() {
		Assertions.assertNotNull(service);
	}

	@Test
	public void testGetProductListFromRepository() {

		// Arrange

		Product product1 = new Product(1, "Agurkai",10, null );
		List<Product> productListArr = Arrays.asList(product1);
		when(productRepository.findAll()).thenReturn(productListArr);

		// Act

		List<Product> productListAct = service.findAll();

		// Assert

		Assertions.assertEquals(productListArr, productListAct);
		verify(productRepository).findAll();

	}

	@Test
	public void testFindProductById() {

		// Arrange

		int productId = 1;
		Optional<Product> product1 = Optional.of(new Product(productId, "Agurkai",10, null));
		when(productRepository.findById(productId)).thenReturn(product1);

		// Act

		Product returnedProduct = service.findById(productId);

		// Assert

		Assertions.assertEquals(productId, returnedProduct.getId());
		verify(productRepository).findById(productId);
	}

	@Test
	public void testSaveProductToRepository() {

		// Arrange

		Product product1 = new Product(1, "Agurkai",10, null);

		// Act

		service.save(product1);

		// Assert

		verify(productRepository).save(product1);
	}
	
	@Test
	public void testDeleteProductFromRepo(){
		
		//Arrange
				
		Product product1 = new Product(1, "Agurkai",10, null);
		
		// Act
		
		service.delete(product1);
		
		//Assert
		
		verify(productRepository).delete(product1);
	}
	
	

}
