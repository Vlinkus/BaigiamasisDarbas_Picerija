package lt.academy.javau5.pizza.exceptions;

public class ProductAlreadyExistException extends RuntimeException{

	
	private static final long serialVersionUID = 5920333280364126041L;
	
	 public ProductAlreadyExistException(String message) {
	        super(message);
	    }
}
