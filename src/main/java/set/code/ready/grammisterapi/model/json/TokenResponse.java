package set.code.ready.grammisterapi.model.json;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TokenResponse {

    private String type;

    private String token;

    private String username;

    private String email;

    private List<String> roles;

}
