package lt.academy.javau5.pizza.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import lt.academy.javau5.pizza.entities.Order;
import lt.academy.javau5.pizza.entities.Pizza;
import lt.academy.javau5.pizza.exceptions.NullCanNotBeSavedException;
import lt.academy.javau5.pizza.exceptions.OrderAlreadyExistsException;
import lt.academy.javau5.pizza.exceptions.OrderDoesNotExistException;
import lt.academy.javau5.pizza.exceptions.PizzaDoesNotExistException;
import lt.academy.javau5.pizza.repositories.OrderRepository;
import lt.academy.javau5.pizza.repositories.PizzaRepository;

@SpringBootTest
public class OrderServiceTests {
	
	@Mock
	OrderRepository repo;
	
	@InjectMocks
	OrderService service;
	
	@Mock
	PizzaRepository pizzaRepo;	
	
	@Test
	void testReturnTrueIfOrderServiceNotNull() {
		Assertions.assertNotNull(service);
	}
	
	@Test
	void testGetOrdersListFromRepository() {
		//Arrange
		Order order = new Order(1, null, 20.0);
		List<Order> ordersList = Arrays.asList(order);
		when(repo.findAll()).thenReturn(ordersList);
		//Act
		List<Order> result = service.findAll();
		//Assert
		Assertions.assertEquals(ordersList, result);
		verify(repo).findAll();
	}
	
	@Test
	void testSaveOrder() {
		//Arrange
		 List<Pizza> pizzas = Arrays.asList(new Pizza("Margarita",null, 10, 10, null ));
		Order order = new Order(pizzas, 20.0);
		when(pizzaRepo.findAll()).thenReturn(pizzas);
		//Act
		service.save(order);
		//Assert
		verify(repo).save(order);
	}
	
	@Test
	void testFindOrderById() {
		//Arrange
		int id = 1;
		 List<Pizza> pizzas = Arrays.asList(new Pizza("Margarita",null, 10, 10, null ));
		Order order = new Order(1, pizzas, 20.0);
		when(repo.findById(id)).thenReturn(Optional.of(order));
		//Act
		service.findById(id);
		//Assert
		verify(repo).findById(id);
	}
	
	@Test
	void testUpdateOrder() {
		//Arrange
		int id = 1;
		 List<Pizza> pizzas = Arrays.asList(new Pizza("Margarita",null, 10, 10, null ));
		 List<Pizza> newPizzas = Arrays.asList(new Pizza("Margarita",null, 10, 10, null ),new Pizza("Kapri",null, 10, 10, null ));
		Order order = new Order(1, pizzas, 20.0);
		Order updatedOrder = new Order(1, newPizzas, 20.0);
		when(repo.findById(id)).thenReturn(Optional.of(order));
		when(pizzaRepo.findAll()).thenReturn(newPizzas);
		//Act
		service.update(updatedOrder);
		//Assert
		verify(repo).save(updatedOrder);
	}
	
	@Test
	void testDeleteOrder() {
		//Arrange
		int id = 2;
		 List<Pizza> pizzas = Arrays.asList(new Pizza("Margarita",null, 10, 10, null ));
		Order order = new Order(1, pizzas, 20.0);
		when(repo.findById(id)).thenReturn(Optional.of(order));
		//Act
		service.deleteOrder(id);
		//Assert
		verify(repo).delete(order);
	}
	
	@Test
	void tryToDeleteOrderWithNotExistingId() {
		//Arrange
		int id = 1;
		int nonExistingId = 2;
		 List<Pizza> pizzas = Arrays.asList(new Pizza("Margarita",null, 10, 10, null ));
		Order order = new Order(id, pizzas, 20.0);
		when(repo.findById(id)).thenReturn(Optional.of(order));
		//Assert
		Assertions.assertThrows(OrderDoesNotExistException.class, () -> {
			service.deleteOrder(nonExistingId);
		},"Order with ID: " + nonExistingId + " was not found" );
		verify(repo, never()).delete(any());
	}
	
	@Test
	void tryToDeleteOrderWithNoId() {
		//Arrange
		int id = 1;
		int nonExistingId = 2;
		 List<Pizza> pizzas = Arrays.asList(new Pizza("Margarita",null, 10, 10, null ));
		Order order = new Order(id, pizzas, 20.0);
		when(repo.findById(id)).thenReturn(Optional.of(order));
		//Assert
		Assertions.assertThrows(OrderDoesNotExistException.class, () -> {
			service.deleteOrder(nonExistingId);
		},"Order with ID: " + nonExistingId + " was not found" );
		verify(repo, never()).delete(any());
	}
	
	@Test
	void testSaveOrderAsNull() {
		//Arrange
		Order order = null;
		//Assert
		Assertions.assertThrows(NullCanNotBeSavedException.class, () -> {
			service.save(order);
		},"Empty Order cannot be saved, add something");
		verify(repo, never()).save(any());
	}
	
	@Test
	void testSaveOrderWithPizzaListAsNull() {
		//Arrange
		Order order = new Order(null, 20.0);
		//Assert
		Assertions.assertThrows(NullCanNotBeSavedException.class, () -> {
			service.save(order);
		},"Empty Order cannot be saved, add something");
		verify(repo, never()).save(any());
	}
	
	@Test
	void testSaveOrderWithIdThatAlreadyExist() {
		//Arrange
		int id = 1;
		List<Pizza> pizzaList = Arrays.asList(
						new Pizza(1, "Margarita",null, 10.0, 10, null, null ),
						new Pizza(2, "Kapri",null, 10.0, 10, null, null ));
		 List<Pizza> pizzas = Arrays.asList(pizzaList.get(0));
		 List<Pizza> newPizzas = Arrays.asList(pizzaList.get(1));
		Optional<Order> order = Optional.of(new Order(1, pizzas, 20.0));
		when(repo.findById(id)).thenReturn(order);
		when(pizzaRepo.findAll()).thenReturn(pizzaList);
		Order newOrder = new Order(1, newPizzas, 10.0);
		//Assert
		Assertions.assertThrows(OrderAlreadyExistsException.class, () -> {
			service.save(newOrder);
		},"Order with ID: " + id + " already exists");
		verify(repo, never()).save(any());
	}
	
	@Test
	void testTryUpdateOrderWithOrderNull() {
		//Arrange
		Order order = null;
		//Assert
		 Assertions.assertThrows(NullCanNotBeSavedException.class, () -> {
		        service.update(order);
		    }, "Empty Order cannot be saved, add something");
		 verify(repo, never()).save(any());
	}
	
	@Test
	void testTryUpdateOrderWithoutId() {
		//Arrange
		Order order = new Order(null, 20.0);
		//Assert
		 Assertions.assertThrows(NullCanNotBeSavedException.class, () -> {
		        service.update(order);
		    }, "Empty Order cannot be saved, add something");
		verify(repo, never()).save(any());
	}
	
	@Test
	void testIfAllPizzasExistInDbBeforeSavingOrdercheck_PizzaDoesNotExist_ThrowException() {
			//Arrange
			 List<Pizza> pizzasFromBd = Arrays.asList(new Pizza("Margarita",null, 10, 10, null ));
			 List<Pizza> pizzas = Arrays.asList(new Pizza("Kapri",null, 10, 10, null ));
			when(pizzaRepo.findAll()).thenReturn(pizzasFromBd);
			OrderService orderService = new OrderService();
			//Assert
			PizzaDoesNotExistException exception = Assertions.assertThrows(PizzaDoesNotExistException.class, () -> {
				service.checkIfPizzasExist(pizzas);
			    }, "Pizza with name: " + pizzas.get(0).getPizzaName() + " not found");
			Assertions.assertEquals("Pizza with name: Kapri not found", exception.getMessage());
	}

	
	

}
