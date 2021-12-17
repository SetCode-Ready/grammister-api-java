package set.code.ready.grammisterapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import set.code.ready.grammisterapi.model.enumeration.RoleEnum;

@Data
@AllArgsConstructor
@Document(collection = "roles")
public class Role {

    @Id
    private String id;

    private RoleEnum name;

}
