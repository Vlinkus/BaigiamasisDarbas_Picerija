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
public class AuthenticationResponse implements AbstractResponse{
    @JsonProperty("access_token")
    private String accessToken;
}
