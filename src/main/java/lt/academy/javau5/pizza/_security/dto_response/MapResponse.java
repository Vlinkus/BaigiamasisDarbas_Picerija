package lt.academy.javau5.pizza._security.dto_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@Builder
public class MapResponse extends HashMap implements AbstractResponse {
}
