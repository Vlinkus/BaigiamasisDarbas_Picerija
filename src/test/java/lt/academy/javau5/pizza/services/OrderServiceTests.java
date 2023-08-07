package lt.academy.javau5.pizza.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import lt.academy.javau5.pizza.entities.Order;
import lt.academy.javau5.pizza.repositories.OrderRepository;

@SpringBootTest
public class OrderServiceTests {
	
	@Mock
	OrderRepository repo;
	
	@InjectMocks
	OrderService service;
	
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
		List<Order> result = repo.findAll();
		//Assert
		Assertions.assertEquals(ordersList, result);	
	}
	
	@Test
	void testSaveOrder() {
		//Arrange
		Order order = new Order(1, null, 20.0);
		//Act
		service.save(order);
		//Assert
		verify(repo).save(order);
	}

}
