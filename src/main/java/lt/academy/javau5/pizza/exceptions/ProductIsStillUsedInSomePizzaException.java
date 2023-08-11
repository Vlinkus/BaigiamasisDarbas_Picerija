package lt.academy.javau5.pizza.exceptions;

public class ProductIsStillUsedInSomePizzaException extends RuntimeException {

	private static final long serialVersionUID = 2202884313269520044L;

	public ProductIsStillUsedInSomePizzaException(String message) {
		super(message);
	}
}
