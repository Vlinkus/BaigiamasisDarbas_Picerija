package lt.academy.javau5.pizza.controllers;

import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import lt.academy.javau5.pizza.services.PizzaService;

@SpringBootTest
public class PizzaControllerTests {
	
	@Mock
	PizzaService service;

	// Pizza(int id, String pizzaName, byte[] pizzaPhoto, double pizzaPrice, int pizzaSize,List<Product> products)
}
