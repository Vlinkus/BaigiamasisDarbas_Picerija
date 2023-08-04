package lt.academy.javau5.pizza.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.academy.javau5.pizza.entities.Order;
import lt.academy.javau5.pizza.exceptions.OrderDoesNotExistException;
import lt.academy.javau5.pizza.entities.OrderResponseEntity;
import lt.academy.javau5.pizza.services.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	//Show all Orders
	
	@GetMapping("/orders")
	public List<Order>findAll(){
		return orderService.findAll();
	}
	
	//Get Order by Id
	
	@GetMapping("/order/{orderId}")
	public Order getCart(@PathVariable int orderId) {
		return orderService.findById(orderId);
	}
	
	//Create Order with pizzas
	
	@PostMapping("/order")
	public OrderResponseEntity createOrder(@RequestBody Order theOrder) {
		Order order= orderService.save(theOrder);
		if(order==null)
		return new OrderResponseEntity(order,HttpStatus.BAD_REQUEST, "Creating order Failed");
		return new OrderResponseEntity(order,HttpStatus.OK, "Order created successesfully");
	}
	
	// Delete Order
	
	@DeleteMapping("/order/{orderId}")
	public ResponseEntity<String> deleteOrder(@PathVariable int orderId){
		try {
			String msg =orderService.deleteOrder(orderId);
			return ResponseEntity.ok(msg);
	    } catch (OrderDoesNotExistException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
		
	}
	
	// Update order
	
		@PutMapping("/order")
		public OrderResponseEntity updateCart(@RequestBody Order order) {
			Order saveOrder = orderService.update(order);
			if(saveOrder ==null)
				return new OrderResponseEntity(saveOrder,HttpStatus.BAD_REQUEST, "Saving Update Failed");
				return new OrderResponseEntity(saveOrder,HttpStatus.OK, "Order Updated Succesfully");
		}


	
}
