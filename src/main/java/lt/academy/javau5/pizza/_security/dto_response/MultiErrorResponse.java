package lt.academy.javau5.pizza._security.dto_response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiErrorResponse implements AbstractResponse {
    private Integer status; // http status code
    private List<String> errors; // error messages
}
