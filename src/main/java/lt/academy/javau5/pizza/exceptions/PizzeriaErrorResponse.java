package lt.academy.javau5.pizza.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PizzeriaErrorResponse {
	private String message;
	private int status;
}
