package lt.academy.javau5.pizza._security.dto_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BroadAuthenticationResponse implements AbstractResponse{
    @JsonProperty("access_token")
    private String accessToken;
    private String role;
}
