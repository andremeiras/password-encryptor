package com.andresantos.passwordencryptor.controller;

import com.andresantos.passwordencryptor.UserModel;
import com.andresantos.passwordencryptor.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<UserModel>> listAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<UserModel> save(@RequestBody UserModel userModel) {
        return ResponseEntity.ok(repository.save(userModel));
    }
}
