package lt.academy.javau5.pizza.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import lt.academy.javau5.pizza.entities.Pizza;
import lt.academy.javau5.pizza.entities.Product;
import lt.academy.javau5.pizza.exceptions.NullCanNotBeSavedException;
import lt.academy.javau5.pizza.exceptions.PizzaAlreadyExistException;
import lt.academy.javau5.pizza.exceptions.PizzaDoesNotExistException;
import lt.academy.javau5.pizza.repositories.PizzaRepository;
import lt.academy.javau5.pizza.repositories.ProductRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PizzaServiceTests {
	
	@Mock
	PizzaRepository repo;
	
	@Mock
	ProductRepository productRepo;
	
	@InjectMocks
	PizzaService service;
	
	@Test
	void TestReturnsTrueIfPizzaServiceNotNull() {
		Assertions.assertNotNull(service);
	}
	
	
	@Test
	public void testGetsListofPizzaFromRepo(){
		//Arrange
		// Pizza(int id, String pizzaName, byte[] pizzaPhoto, double pizzaPrice, int pizzaSize,List<Product> products)
		Pizza p1 = new Pizza("Margarita",null, 10, 10, null );
		Pizza p2 = new Pizza("Hawaian", null, 20.0, 20, null);
		List<Pizza> pList = Arrays.asList(p1,p2);
		when(repo.findAll()).thenReturn(pList);
		// Act
		List<Pizza> listc = service.findAll();
		//Assert
		Assertions.assertEquals(pList, listc);
		verify(repo).findAll();
	}
	
	@Test
	public void testFindPizzaById(){
		//Arrange
		int pizzaId = 1;
		Optional<Pizza> p1 = Optional.of(new Pizza(1,"Margarita", null, 10.0, 20, null, null));
		when(repo.findById(pizzaId)).thenReturn(p1);
		// Act
		Pizza returnedPizza = service.findById(pizzaId);
		//Assert
		Assertions.assertEquals(pizzaId, returnedPizza.getId());
		verify(repo).findById(pizzaId);
	}
	
	@Test
	public void testSavePizzaToRepo(){
		//Arrange
		// Pizza(int id, String pizzaName, byte[] pizzaPhoto, double pizzaPrice, int pizzaSize,List<Product> products,  List<Order> orders)
		Pizza p = new Pizza("Margarita", null, 10.0, 20 , null);
		// Act
		service.save(p);
		//Assert
		verify(repo).save(p);
	}
	
	@Test
	public void testUpdatePizzaToRepo(){
		//Arrange
		// Pizza(int id, String pizzaName, byte[] pizzaPhoto, double pizzaPrice, int pizzaSize,List<Product> products,  List<Order> orders)
		int id = 1;
		Pizza p = new Pizza(1, "Margarita", null, 10.0, 20 , null, null);
		Optional<Pizza> pizzaFromRepo = Optional.of(new Pizza(1, "Margarita", null, 10.0, 20 , null, null));
		when(repo.findById(id)).thenReturn(pizzaFromRepo);
		// Act
		service.update(p);
		//Assert
		verify(repo).save(p);
	}
	
	@Test
	public void testDeletePizzaFromRepo(){
		//Arrange
		// Pizza(int id, String pizzaName, byte[] pizzaPhoto, double pizzaPrice, int pizzaSize,List<Product> products)
		int id  = 1;
		Optional<Pizza> p1 = Optional.of(new Pizza(1,"Margarita", null, 10.0, 20, null, null));
		when(repo.findById(id)).thenReturn(p1);
		// Act
		Pizza pizzaToDelete = service.findById(id);
		service.deletePizza(id);
		//Assert
		verify(repo).delete(pizzaToDelete);
	}
	
	
	@Test
	public void testUploadPizzaPhotoToRepo(){
		//Arrange
		// Pizza(int id, String pizzaName, byte[] pizzaPhoto, double pizzaPrice, int pizzaSize,List<Product> products)
		int id =  1;
		Optional<Pizza> p1 = Optional.of(new Pizza("Margarita", null, 10.0, 20, null));
		String mockImagePath = "path/Pizzeriaa_BackEnd/src/test/java/lt/academy/javau5/pizza/testPhoto.jpg";
		byte[] photoBytes = null;
		when(repo.findById(id)).thenReturn(p1);
		// Act
		service.uploadPizzaPhoto(id,photoBytes);
		//Assert
		verify(repo).save(p1.get());
		verify(repo).findById(id);
	}
	
	
    @Test
    public void testUpdatePizzaWithoutId() {
        // Arrange
        Pizza pizzaToUpdate = new Pizza(null, "Margarita", null, 10.0, 20, null, null);

        // Act and Assert
        Assertions.assertThrows(PizzaDoesNotExistException.class, () -> {
            service.update(pizzaToUpdate);
        }, "Pizza with ID: null does not exist");
    }

	@Test
	public void testTryToUpdatePizzaWithNonExistingId() {
		// Arrange
		int pizzaId = 1;
		Pizza pizzaToUpdate = new Pizza(pizzaId, "Margarita", null, 10.0, 20, null, null);
		when(repo.findById(pizzaId)).thenReturn(java.util.Optional.empty());
		// Act and Assert
		PizzaDoesNotExistException exception = Assertions.assertThrows(PizzaDoesNotExistException.class, () -> {
			service.update(pizzaToUpdate);
		});
		Assertions.assertEquals("Pizza with ID: " + pizzaId + " does  not exist", exception.getMessage());
		verify(repo, never()).save(any());
	}
    
    @Test
    public void testTryUpdatePizzaAsNull() {
    	//Arrange
    	Pizza pizzaToUpdate = null;
        // Act and Assert
    	PizzaDoesNotExistException exception = Assertions.assertThrows(PizzaDoesNotExistException.class, () -> {
            service.update(pizzaToUpdate);
        }, "Empty pizza can not be saved");
    	Assertions.assertEquals("Pizza can not be updated", exception.getMessage());
        verify(repo, never()).findById(any());
        verify(repo, never()).save(any());
    }
    
    @Test
    public void testTrySavePizzaWithPizzaNameNull() {
    	//Arrange
    	Pizza pizzaToUpdate = new Pizza(null, null, 10.0, 20, null, null);
        // Act and Assert
    	NullCanNotBeSavedException exception = Assertions.assertThrows(NullCanNotBeSavedException.class, () -> {
            service.save(pizzaToUpdate);
        }, "Empty pizza can not be saved");
    	Assertions.assertEquals("Empty pizza can not be saved", exception.getMessage());
        verify(repo, never()).findById(any());
        verify(repo, never()).save(any());
    }
    
    @Test
    public void testTryUpdatePizzaWithPizzaNameNull() {
    	//Arrange
    	Pizza pizzaToUpdate = new Pizza(1, null, null, 10.0, 20, null, null);
        // Act and Assert
    	NullCanNotBeSavedException exception = Assertions.assertThrows(NullCanNotBeSavedException.class, () -> {
            service.update(pizzaToUpdate);
        }, "Empty pizza can not be saved");
    	Assertions.assertEquals("Pizza can not be updated if name is empty", exception.getMessage());
        verify(repo, never()).findById(any());
        verify(repo, never()).save(any());
    }
    
    @Test
    public void testTryUpdatePizzaWithPizzaNameEmpty() {
    	//Arrange
    	Pizza pizzaToUpdate = new Pizza(1, "", null, 10.0, 20, null, null);
        // Act and Assert
    	NullCanNotBeSavedException exception = Assertions.assertThrows(NullCanNotBeSavedException.class, () -> {
            service.update(pizzaToUpdate);
        }, "Empty pizza can not be saved");
    	Assertions.assertEquals("Pizza can not be updated if name is empty", exception.getMessage());
        verify(repo, never()).findById(any());
        verify(repo, never()).save(any());
    }
    
    @Test
    public void testTrySavePizzaWithPizzaNameEmpty() {
    	//Arrange
    	Pizza pizzaToUpdate = new Pizza(1, "", null, 10.0, 20, null, null);
        // Act and Assert
    	NullCanNotBeSavedException exception = Assertions.assertThrows(NullCanNotBeSavedException.class, () -> {
            service.save(pizzaToUpdate);
        }, "Empty pizza can not be saved");
    	Assertions.assertEquals("Empty pizza can not be saved", exception.getMessage());
        verify(repo, never()).findById(any());
        verify(repo, never()).save(any());
    }
    

    @Test
    public void testSavePizzaWithExistingName() {
        // Arrange
        int pizzaId = 1;
        String pizzaName = "Existing Pizza";
        Pizza existingPizza = new Pizza(1, pizzaName, null, 10.0, 20, null, null);
        when(repo.findPizzaByPizzaName(pizzaName)).thenReturn(java.util.Optional.of(existingPizza));
        // Act
        Pizza updatedPizza = new Pizza(pizzaId + 1, pizzaName, null, 12.0, 25, null, null);
        // Assert
        Assertions.assertThrows(PizzaAlreadyExistException.class, () -> {
            service.save(updatedPizza);
        }, "Pizza with name: " + pizzaName + " already exists");
        verify(repo, never()).findById(any());
        verify(repo, never()).save(any());
    }
    
    @Test
    public void testSavePizzaWithIdFieldButIdIsNotIndataBase() {
        // Arrange
        String pizzaName = "Kapri";
        Pizza existingPizza = new Pizza(1, pizzaName, null, 10.0, 20, null, null);
        when(repo.findPizzaByPizzaName(pizzaName)).thenReturn(java.util.Optional.of(existingPizza));
        // Act
        Pizza newPizza = new Pizza(2, pizzaName, null, 12.0, 25, null, null);
        // Assert
        Assertions.assertThrows(PizzaAlreadyExistException.class, () -> {
            service.save(newPizza);
        }, "Pizza with name: " + pizzaName + " already exists");
        verify(repo, never()).findById(any());
        verify(repo, never()).save(any());
    }
    
    @Test
    public void testTryToSavePizzaWithExistingId() {
        // Arrange
        int id = 1;
        Pizza existingPizza = new Pizza(id, "Kapri", null, 10.0, 20, null, null);
        when(repo.findById(id)).thenReturn(java.util.Optional.of(existingPizza));
        // Act
        Pizza newPizza = new Pizza(id, "Margarita", null, 12.0, 25, null, null);
        // Assert
        Assertions.assertThrows(PizzaAlreadyExistException.class, () -> {
            service.save(newPizza);
        }, "Pizza with ID: " + newPizza.getId() + " already exists");
        verify(repo, never()).save(any());
    }
    
    @Test
    public void testCheckIfAllProductsInPizzaAreSavedInDataBase_PizzaHasNoProducts() {
        // Arrange
        Pizza pizza =  new Pizza(1, "Kapri", null, 10.0, 20, null, null);
        pizza.setProducts(null);
        // Act
        List<Product> productsInPizza = service.checkIfAllProductsInPizzaAreSavedInDataBase(pizza);

        // Assert
        Assertions.assertTrue(productsInPizza.isEmpty());
    }
    
    @Test
    public void testCheckIfAllProductsInPizzaAreSavedInDataBase_ProductsExistInDB() {
        // Arrange
        Pizza pizza = new Pizza();
        List<Product> productsInPizza = new ArrayList<>();
        Product product1 = new Product(1, "Cheese", 10.0, null);
        Product product2 = new Product(2, "Bacon", 15.0, null);
        productsInPizza.add(product1);
        productsInPizza.add(product2);
        pizza.setProducts(productsInPizza);
        List<Product> allProductsInDB = new ArrayList<>();
        allProductsInDB.add(product1);
        allProductsInDB.add(product2);
        when(productRepo.findAll()).thenReturn(allProductsInDB);
        // Act
        List<Product> resultProducts = service.checkIfAllProductsInPizzaAreSavedInDataBase(pizza);
        // Assert
        Assertions.assertEquals(allProductsInDB.size(), resultProducts.size());
        Assertions.assertTrue(resultProducts.containsAll(allProductsInDB));
    }

    @Test
    public void testCheckIfAllProductsInPizzaAreSavedInDataBase_ProductsNeedSaving() {
        // Arrange
        Pizza pizza = new Pizza();
        List<Product> productsInPizza = new ArrayList();
        Product newProduct1 = new Product("New Product 1", 8.0);
        Product newProduct2 = new Product("New Product 2", 12.0);
        productsInPizza.add(newProduct1);
        productsInPizza.add(newProduct2);
        pizza.setProducts(productsInPizza);
        when(productRepo.findAll()).thenReturn(new ArrayList<>());
        when(productRepo.save(any(Product.class)))
                .thenAnswer(invocation -> {
                    Product productToSave = invocation.getArgument(0);
                    productToSave.setId(1);
                    return productToSave;
                });
        // Act
        List<Product> resultProducts = service.checkIfAllProductsInPizzaAreSavedInDataBase(pizza);
        // Assert
        Assertions.assertEquals(productsInPizza.size(), resultProducts.size());
        Assertions.assertEquals(1, resultProducts.get(0).getId());
        Assertions.assertEquals(1, resultProducts.get(1).getId());
    }

}
