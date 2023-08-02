package lt.academy.javau5.pizza.exceptions;

public class PizzaDoesNotExistException  extends IllegalArgumentException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9021369661674429596L;
	public PizzaDoesNotExistException(String msg) {
		super(msg);
	}
}
