package lt.academy.javau5.pizza.exceptions;

public class ProductDoesNotExistExecption extends IllegalArgumentException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4021086653408961332L;

	public ProductDoesNotExistExecption(String msg) {
		super(msg);
	}

}
