package lt.academy.javau5.pizza.controllers;

import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import lt.academy.javau5.pizza.services.ProductService;

@SpringBootTest
public class ProductControllerTests {

	
	@Mock
	ProductService service;
}
