package lt.academy.javau5.pizza.controllers;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lt.academy.javau5.pizza.services.PizzaService;

@SpringBootTest
public class PizzaControllerTests {
	
	@Mock
	PizzaService service;

}
