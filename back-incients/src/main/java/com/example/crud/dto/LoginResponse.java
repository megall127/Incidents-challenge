package com.example.crud.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta de login de usuário")
public class LoginResponse {
    
    @Schema(description = "Token JWT", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;
    
    @Schema(description = "Nome do usuário", example = "João Silva")
    private String nome;
    
    @Schema(description = "Mensagem de confirmação", example = "Login realizado com sucesso!")
    private String message;

    public LoginResponse() {}

    public LoginResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public LoginResponse(String token, String nome, String message) {
        this.token = token;
        this.nome = nome;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
