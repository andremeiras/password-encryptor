package com.andresantos.passwordencryptor.controller;

import com.andresantos.passwordencryptor.UserModel;
import com.andresantos.passwordencryptor.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserController(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<UserModel>> listAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<UserModel> save(@RequestBody UserModel userModel) {
        userModel.setPassword(encoder.encode(userModel.getPassword()));
        return ResponseEntity.ok(repository.save(userModel));
    }

    @GetMapping("/validatePass")
    public ResponseEntity<Boolean> validatePassword(@RequestParam String login, @RequestParam String password) {


        Optional<UserModel> optUser = repository.findByLogin(login);
        if (optUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        UserModel user = optUser.get();
        boolean valid = encoder.matches(password, user.getPassword());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);

    }
}
