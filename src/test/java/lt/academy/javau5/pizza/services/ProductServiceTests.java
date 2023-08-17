package lt.academy.javau5.pizza.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import lt.academy.javau5.pizza.exceptions.NullCanNotBeSavedException;
import lt.academy.javau5.pizza.exceptions.ProductAlreadyExistException;
import lt.academy.javau5.pizza.exceptions.ProductDoesNotExistExecption;
import lt.academy.javau5.pizza.exceptions.ProductIsStillUsedInSomePizzaException;
import lt.academy.javau5.pizza.repositories.ProductRepository;

@SpringBootTest
public class ProductServiceTests {

	@Mock
	ProductRepository repo;

	@InjectMocks
	ProductService service;

	@Test
	void testReturnTrueIfProductServiceNotNull() {
		Assertions.assertNotNull(service);
	}

	@Test
	public void testGetProductListFromRepository() {
		// Arrange
		Product product1 = new Product("Agurkai", 10);
		List<Product> productListArr = Arrays.asList(product1);
		when(repo.findAll()).thenReturn(productListArr);
		// Act
		List<Product> productListAct = service.findAll();
		// Assert
		Assertions.assertEquals(productListArr, productListAct);
		verify(repo).findAll();
	}

	@Test
	public void testFindProductById() {
		// Arrange
		int productId = 1;
		Optional<Product> product1 = Optional.of(new Product(1, "Agurkai", 10.0, null));
		when(repo.findById(productId)).thenReturn(product1);
		// Act
		Product returnedProduct = service.findById(productId);
		// Assert
		Assertions.assertEquals(productId, returnedProduct.getId());
		verify(repo).findById(productId);
	}

	@Test
	public void testSaveProductToRepository() {
		// Arrange
		Product product1 = new Product("Agurkai", 10);
		// Act
		service.save(product1);
		// Assert
		verify(repo).save(product1);
	}

	@Test
	public void testUpdateProductToRepository() {
		// Arrange
		int id = 1;
		Optional<Product> existingProduct = Optional.of(new Product(id, "Agurkai", 10.0, null));
		Product product = new Product(id, "Agurkai", 10.0, null);
		when(repo.findById(id)).thenReturn(existingProduct);
		// Assert
		service.update(product);
		// Assert
		verify(repo).save(product);
	}

	@Test
	public void testDeleteProductFromRepo() {
		// Arrange
		int id = 1;
		Optional<Product> product = Optional.of(new Product(1, "Agurkai", 10.0, null));
		Product prod = product.get();
		when(repo.findById(id)).thenReturn(product);
		// Act
		String result = service.delete(id);
		// Assert
		Assertions.assertEquals("Product Deleted Succesfully", result);
		verify(repo).delete(prod);
	}

	@Test
	public void testDeleteProductFromRepoWhenPizzaListIsEmpty() {
		// Arrange
		int id = 1;
		List<Pizza> pizzas = new ArrayList<>();
		Optional<Product> product = Optional.of( new Product(1, "Agurkai", 10.0, pizzas));
		Product prod =product.get();
		when(repo.findById(id)).thenReturn(product);
		// Act
		String result = service.delete(id);
		// Assert
		Assertions.assertEquals("Product Deleted Succesfully",result);
		verify(repo).delete(prod);
	}
	
	@Test
	public void testTrySaveProductWithExistingName(){
		// Arrange
		String productName = "Agurkai";
		Product product = new Product(1, productName, 10.0, null);
		// Act
		when(repo.findProductByProductName(productName)).thenReturn(product);
		// Assert
		Assertions.assertThrows(ProductAlreadyExistException.class, () -> {
			service.save(product);
		}, "Product with this name already exist");
		verify(repo, never()).save(any());
	}

	@Test
	public void testTrySaveProductWithNameNull() {
		// Arrange
		Product product = new Product(1, null, 10.0, null);
		// Assert
		Assertions.assertThrows(NullCanNotBeSavedException.class, () -> {
			service.save(product);
		}, "Empty product can not be saved");

		verify(repo, never()).save(any());
	}

	@Test
	public void testTrySaveProductWithNameEmpty() {
		// Arrange
		Product product = new Product("", 10.0);
		// Assert
		Assertions.assertThrows(NullCanNotBeSavedException.class, () -> {
			service.save(product);
		}, "Empty product can not be saved");

		verify(repo, never()).save(any());
	}

	@Test
	public void testTrySaveProductAsNull() {
		// Arrange
		Product product = null;
		// Assert
		Assertions.assertThrows(NullCanNotBeSavedException.class, () -> {
			service.save(product);
		}, "Empty product can not be saved");
		verify(repo, never()).save(any());
	}

	@Test
	public void testTrySaveProductWithExistingId() {
		// Arrange
		int id = 1;
		Optional<Product> existingProduct = Optional.of(new Product(id, "Agurkai", 10.0, null));
		Product product = new Product(id, "Pomidorai", 10.0, null);
		when(repo.findById(id)).thenReturn(existingProduct);
		// Assert
		Assertions.assertThrows(ProductAlreadyExistException.class, () -> {
			service.save(product);
		}, "Product with ID:" + id + "id already exist");

		verify(repo, never()).save(any());
	}

	@Test
	public void testTryUpdateProductAsNull() {
		// Arrange
		Product product = null;
		// Assert
		Assertions.assertThrows(ProductDoesNotExistExecption.class, () -> {
			service.update(product);
		}, "Product can not be updated");
		verify(repo, never()).save(any());
	}

	@Test
	public void testTryUpdateProductWithProductNameNull() {
		// Arrange
		Product product = new Product(1, null, 10.0, null);
		// Assert
		Assertions.assertThrows(NullCanNotBeSavedException.class, () -> {
			service.update(product);
		}, "Product can not be updated with empty name");
		verify(repo, never()).save(any());
	}

	@Test
	public void testTryUpdateProductWithProductNameEmpty() {
		// Arrange
		Product product = new Product(1, "", 10.0, null);
		// Assert
		Assertions.assertThrows(NullCanNotBeSavedException.class, () -> {
			service.update(product);
		}, "Product can not be updated with empty name");
		verify(repo, never()).save(any());
	}

	@Test
	public void testTryUpdateProductWithoutId() {
		// Arrange
		Product product = new Product("Agurkai", 10.0);
		// Assert
		Assertions.assertThrows(ProductDoesNotExistExecption.class, () -> {
			service.update(product);
		}, "Product can not be updated");
		verify(repo, never()).save(any());
	}

	@Test
	public void testTryUpdateProductWithIdThatDoesNotExist() {
		// Arrange
		int id = 1;
		Optional<Product> existingProduct = Optional.of(new Product(id, "Agurkai", 10.0, null));
		Product product = new Product(2, "Pomidorai", 10.0, null);
		when(repo.findById(id)).thenReturn(existingProduct);
		// Assert
		Assertions.assertThrows(ProductDoesNotExistExecption.class, () -> {
			service.update(product);
		}, "Product can not be updated");
		verify(repo, never()).save(any());
	}

	@Test
	public void testTryDeleteProductThatIsUsedInAnyPizza() {
		// Arrange
		int productId = 1;
		List<Pizza> pizzas = Arrays.asList(new Pizza(1, "Pizza", null, 10.0, 32, null, null));
		Product product = new Product(productId, "Cheese", 5.0, pizzas);
		when(repo.findById(productId)).thenReturn(Optional.of(product));
		// Act and Assert
		Assertions.assertThrows(ProductIsStillUsedInSomePizzaException.class, () -> {
			service.delete(productId);
		}, "Product with ID: " + productId + " is associated with other entities and cannot be deleted");
		verify(repo, times(1)).findById(productId);
		verify(repo, never()).delete(product);
	}

}
