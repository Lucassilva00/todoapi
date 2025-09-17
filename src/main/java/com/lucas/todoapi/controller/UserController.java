package com.lucas.todoapi.controller;

import com.lucas.todoapi.model.User;
import com.lucas.todoapi.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user){
        if(userRepository.existsByEmail(user.getEmail())){
            return ResponseEntity.badRequest().body("E-mail já cadastrado!");
        }

        user.setSenha(passwordEncoder.encode(user.getSenha()));


        userRepository.save(user);
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }
}
