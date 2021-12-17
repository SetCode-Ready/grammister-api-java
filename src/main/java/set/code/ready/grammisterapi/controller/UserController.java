package set.code.ready.grammisterapi.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import set.code.ready.grammisterapi.model.User;
import set.code.ready.grammisterapi.model.json.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import set.code.ready.grammisterapi.repository.UserRepository;
import set.code.ready.grammisterapi.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @NonNull
    private final UserService userService;


    @GetMapping()
    public ResponseEntity<Object> getAllUsers() {
        try {
            return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error on find users.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
