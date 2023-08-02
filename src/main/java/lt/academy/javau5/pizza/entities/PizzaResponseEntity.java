package lt.academy.javau5.pizza.entities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class PizzaResponseEntity extends ResponseEntity<Pizza>{

	private String message;
	
	 public PizzaResponseEntity(Pizza pizza, HttpStatus status, String message) {
	        super(pizza, status);
	        this.setMessage(message);
	    }
	 
	 public PizzaResponseEntity(Pizza pizza, HttpStatus status) {
	        super(pizza, status);
	    }

    public PizzaResponseEntity(Pizza pizza, MultiValueMap<String, String> headers, HttpStatus status) {
        super(pizza, headers, status);
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
