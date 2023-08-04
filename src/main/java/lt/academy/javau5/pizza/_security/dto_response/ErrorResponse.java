package lt.academy.javau5.pizza._security.dto_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse implements AbstractResponse {
    private Integer status; // http status code
    private String error; // error message
}
