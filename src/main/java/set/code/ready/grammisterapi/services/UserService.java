package set.code.ready.grammisterapi.services;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import set.code.ready.grammisterapi.model.Role;
import set.code.ready.grammisterapi.model.User;
import set.code.ready.grammisterapi.model.enumeration.RoleEnum;
import set.code.ready.grammisterapi.model.json.UserRequest;
import org.springframework.stereotype.Service;
import set.code.ready.grammisterapi.repository.RoleRepository;
import set.code.ready.grammisterapi.repository.UserRepository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service("UserService")
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    public Collection<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User register(UserRequest userRequest) throws Exception {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new Exception("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new Exception("Error: Email is already in use!");
        }

        User newUser = User.builder()
                .username(userRequest.getUsername())
                .birthdate(userRequest.getBirthdate())
                .gender(userRequest.getGender())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();

        Set<String> strRoles = userRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        newUser.setRoles(roles);

        return userRepository.save(newUser);
    }
}
