package lt.academy.javau5.pizza._security.dto_response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgResponse implements AbstractResponse {
    private String msg;
}
