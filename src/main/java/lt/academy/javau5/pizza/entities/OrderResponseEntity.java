package lt.academy.javau5.pizza.entities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class OrderResponseEntity extends ResponseEntity<Order> {

	private String message;

	public OrderResponseEntity(Order cart, HttpStatus status, String message) {
		super(cart, status);
		this.setMessage(message);
	}

	public OrderResponseEntity(Order cart, HttpStatus status) {
		super(cart, status);
	}

	public OrderResponseEntity(Order cart, MultiValueMap<String, String> headers, HttpStatus status) {
		super(cart, headers, status);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
