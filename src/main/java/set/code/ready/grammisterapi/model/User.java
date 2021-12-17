package set.code.ready.grammisterapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

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

    @JsonIgnore
    private String password;

    @DBRef
    @Builder.Default
    private List<User> followers = new ArrayList<>();

    @DBRef
    @Builder.Default
    private List<User> following = new ArrayList<>();

    private String createdAt;

    private String updatedAt;

    @DBRef
    private Set<Role> roles = new HashSet<>();

}
