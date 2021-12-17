package set.code.ready.grammisterapi.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import set.code.ready.grammisterapi.model.User;
import set.code.ready.grammisterapi.model.json.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import set.code.ready.grammisterapi.repository.UserRepository;
import set.code.ready.grammisterapi.services.UserService;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @NonNull
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        try {
            return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error on find users.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/followUser/{userId}")
    public ResponseEntity<Object> followUser(@PathVariable("userId") String userId) {
        try {
            return new ResponseEntity<>(userService.followUser(userId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
