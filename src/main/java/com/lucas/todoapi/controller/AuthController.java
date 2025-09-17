package com.lucas.todoapi.controller;

import com.lucas.todoapi.DTO.LoginRequest;
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
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest){
        //Verifica se esxiste o email
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null){
            return ResponseEntity.status(401).body("E-mail ou senha inválidos");
        }

        //Verifica se a senha está correta
        if(!passwordEncoder.matches(loginRequest.getSenha(), user.getSenha())){
            return ResponseEntity.status(401).body("E-mail ou senha inválidos");
        }

        //Login feito com sucesso!
        return ResponseEntity.ok("Login realizado com sucesso. Bem-vindo "+user.getNome()+ "!");
    }
}
