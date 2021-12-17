package set.code.ready.grammisterapi.model.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String username;

    private String birthdate;

    private String gender;

    private String email;

    private String password;

    private Set<String> roles;

}
