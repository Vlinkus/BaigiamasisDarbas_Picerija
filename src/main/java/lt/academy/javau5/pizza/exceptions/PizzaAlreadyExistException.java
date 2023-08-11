package lt.academy.javau5.pizza.exceptions;

public class PizzaAlreadyExistException extends IllegalArgumentException {

	private static final long serialVersionUID = 3922762524254094577L;

	public PizzaAlreadyExistException(String message) {
		super(message);
	}

}
