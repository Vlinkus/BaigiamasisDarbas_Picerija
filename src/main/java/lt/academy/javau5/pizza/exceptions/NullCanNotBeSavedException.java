package lt.academy.javau5.pizza.exceptions;

public class NullCanNotBeSavedException extends NullPointerException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8909230195267566444L;
	public NullCanNotBeSavedException(String msg) {
		super(msg);
	}

}
