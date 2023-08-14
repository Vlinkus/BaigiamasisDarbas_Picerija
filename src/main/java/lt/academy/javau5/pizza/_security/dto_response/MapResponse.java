package lt.academy.javau5.pizza._security.dto_response;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class MapResponse extends HashMap implements AbstractResponse {
}
