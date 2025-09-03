package com.example.crud.controller;

import com.example.crud.dto.LoginResponse;
import com.example.crud.dto.LoginRequest;
import com.example.crud.dto.RegisterRequest;
import com.example.crud.dto.RegisterResponse;
import com.example.crud.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para cadastro e login de usuários")
public class AuthController {

    @Autowired
    private AuthService authService;


    @Operation(summary = "Cadastrar novo usuário", description = "Cria uma nova conta de usuário.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Email já cadastrado ou dados inválidos")
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        try {
            RegisterResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new RegisterResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Fazer login", description = "Autentica um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Email ou senha inválidos")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            System.out.println("=== LOGIN DEBUG ===");
            System.out.println("Login attempt for email: " + request.getEmail());
            
            LoginResponse response = authService.login(request);
            System.out.println("Login successful for user: " + response.getNome());
            System.out.println("Token generated: " + (response.getToken() != null ? response.getToken().substring(0, 20) + "..." : "null"));
            System.out.println("=== END LOGIN DEBUG ===");
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.err.println("Login failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(new LoginResponse(null, null, e.getMessage()));
        }
    }
}

