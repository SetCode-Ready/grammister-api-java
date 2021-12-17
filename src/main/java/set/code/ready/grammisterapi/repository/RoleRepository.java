package set.code.ready.grammisterapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import set.code.ready.grammisterapi.model.Role;
import set.code.ready.grammisterapi.model.enumeration.RoleEnum;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByName(RoleEnum name);
}
