package lt.academy.javau5.pizza.exceptions;

public class OrderDoesNotExistException extends IllegalArgumentException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderDoesNotExistException(String msg) {
		super (msg);
	}
}
