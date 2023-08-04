package lt.academy.javau5.pizza.exceptions;

public class OrderAlreadyExistsException extends IllegalArgumentException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	public OrderAlreadyExistsException(String message) {
        super(message);
    }
}
