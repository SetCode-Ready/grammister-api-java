package set.code.ready.grammisterapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;

    private String birthdate;

    private String gender;

    private String email;

    private String password;

    private List followers;

    private List following;

    private String createdAt;

    private String updatedAt;

    @DBRef
    private Set<Role> roles = new HashSet<>();

}
