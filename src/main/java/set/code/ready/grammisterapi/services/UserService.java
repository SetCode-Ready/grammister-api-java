package set.code.ready.grammisterapi.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import set.code.ready.grammisterapi.model.Role;
import set.code.ready.grammisterapi.model.User;
import set.code.ready.grammisterapi.model.UserDetailsImpl;
import set.code.ready.grammisterapi.model.enumeration.RoleEnum;
import set.code.ready.grammisterapi.model.json.UserRequest;
import org.springframework.stereotype.Service;
import set.code.ready.grammisterapi.repository.RoleRepository;
import set.code.ready.grammisterapi.repository.UserRepository;

import java.util.*;

@Service
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
                .createdAt(new Date().toString())
                .updatedAt(new Date().toString())
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

    public String followUser(String userId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (userDetails.getId().equals(userId)) {
            throw new Exception("You can't follow yourself!");
        }

        Optional<User> currentUserFound = userRepository.findById(userDetails.getId());
        Optional<User> userToFollowFound = userRepository.findById(userId);

        if (!currentUserFound.isPresent() && !userToFollowFound.isPresent()) {
            throw new Exception("User(s) not found.");
        }

        try {
            User currentUser = currentUserFound.get();
            User userToFollow = userToFollowFound.get();

            List<User> currentUserFollowings = new ArrayList<>(currentUser.getFollowing());
            List<User> userToFollowFollowers = new ArrayList<>(userToFollow.getFollowers());

            Boolean userIsFollowed = currentUserFollowings.stream()
                    .filter(user -> user.getId().equals(userToFollow.getId())).count() > 0;

            if (!userIsFollowed) {
                currentUserFollowings.add(userToFollow);
                currentUser.setFollowing(currentUserFollowings);

                userToFollowFollowers.add(currentUser);
                userToFollow.setFollowers(userToFollowFollowers);

                userRepository.saveAll(Arrays.asList(currentUser, userToFollow));

                return "You are following the user: " + userToFollow.getUsername();
            } else {
                currentUserFollowings.removeIf(user -> user.getId().equals(userToFollow.getId()));
                currentUser.setFollowing(currentUserFollowings);

                userToFollowFollowers.removeIf(user -> user.getId().equals(currentUser.getId()));
                userToFollow.setFollowers(userToFollowFollowers);

                userRepository.saveAll(Arrays.asList(currentUser, userToFollow));

                return "You unfollowed the user: " + userToFollow.getUsername();
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
