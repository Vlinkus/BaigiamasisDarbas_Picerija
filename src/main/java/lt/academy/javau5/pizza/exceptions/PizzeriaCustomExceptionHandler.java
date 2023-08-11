package lt.academy.javau5.pizza.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PizzeriaCustomExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger exceptionHandlerLogger = LoggerFactory.getLogger(PizzeriaCustomExceptionHandler.class);

	@ExceptionHandler(PizzaAlreadyExistException.class)
	public ResponseEntity<Object> handlePizzaAlreadyExistException(Exception ex, WebRequest request) {
		exceptionHandlerLogger.error("PizzaAlreadyExistException occurred: " + ex.getMessage());
		// Extract relevant information from the exception
		String message = ex.getMessage();
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		// Create a custom error response
		PizzeriaErrorResponse errorResponse = new PizzeriaErrorResponse(message, status.value());
		// Return the custom error response
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(PizzaDoesNotExistException.class)
	public ResponseEntity<Object> handlePizzaDoesNotExistException(Exception ex, WebRequest request) {
		exceptionHandlerLogger.error("PizzaDoesNotExistException occurred: " + ex.getMessage());
		String message = ex.getMessage();
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		PizzeriaErrorResponse errorResponse = new PizzeriaErrorResponse(message, status.value());
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(ProductAlreadyExistException.class)
	public ResponseEntity<Object> handleProductAlreadyExistException(Exception ex, WebRequest request) {
		exceptionHandlerLogger.error("ProductAlreadyExistException occurred: " + ex.getMessage());
		String message = ex.getMessage();
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		PizzeriaErrorResponse errorResponse = new PizzeriaErrorResponse(message, status.value());
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(ProductDoesNotExistExecption.class)
	public ResponseEntity<Object> handleProductDoesNotExistExecption(Exception ex, WebRequest request) {
		exceptionHandlerLogger.error("ProductDoesNotExistExecption occurred: " + ex.getMessage());
		String message = ex.getMessage();
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		PizzeriaErrorResponse errorResponse = new PizzeriaErrorResponse(message, status.value());
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(ProductIsStillUsedInSomePizzaException.class)
	public ResponseEntity<Object> handleProductIsStillUsedInSomePizzaException(Exception ex, WebRequest request) {
		exceptionHandlerLogger.error("ProductIsStillUsedInSomePizzaException occurred: " + ex.getMessage());
		String message = ex.getMessage();
		HttpStatus status = HttpStatus.CONFLICT;
		PizzeriaErrorResponse errorResponse = new PizzeriaErrorResponse(message, status.value());
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NullCanNotBeSavedException.class)
	public ResponseEntity<Object> handlePizzaNullCanNotBeSavedException(Exception ex, WebRequest request) {
		exceptionHandlerLogger.error("ProductIsStillUsedInSomePizzaException occurred: " + ex.getMessage());
		String message = ex.getMessage();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		PizzeriaErrorResponse errorResponse = new PizzeriaErrorResponse(message, status.value());
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
	}
}
