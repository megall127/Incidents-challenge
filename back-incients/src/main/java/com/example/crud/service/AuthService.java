package com.example.crud.service;

import com.example.crud.dto.AuthResponse;
import com.example.crud.dto.LoginRequest;
import com.example.crud.dto.RegisterRequest;
import com.example.crud.model.User;
import com.example.crud.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {

        User existingUser = usersRepository.findByEmail(request.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("Email já cadastrado");
        }


        User user = new User();
        user.setNome(request.getNome());
        user.setEmail(request.getEmail());
        user.setSenha(passwordEncoder.encode(request.getSenha()));


        User savedUser = usersRepository.save(user);


        String token = jwtService.generateToken(savedUser.getEmail());

        return new AuthResponse(token, "Usuário cadastrado com sucesso!");
    }

    public AuthResponse login(LoginRequest request) {

        User user = usersRepository.findByEmail(request.getEmail());
        
        if (user == null) {
            throw new RuntimeException("Email ou senha inválidos");
        }

  
        if (!passwordEncoder.matches(request.getSenha(), user.getSenha())) {
            throw new RuntimeException("Email ou senha inválidos");
        }

    
        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token, "Login realizado com sucesso!");
    }
}

