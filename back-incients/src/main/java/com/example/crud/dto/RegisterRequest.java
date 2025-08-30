package com.example.crud.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para cadastro de usuário")
public class RegisterRequest {
    @Schema(description = "Nome completo do usuário", example = "Leandro Wilker")
    private String nome;
    
    @Schema(description = "Email do usuário", example = "leandro@email.com")
    private String email;
    
    @Schema(description = "Senha do usuário", example = "123456")
    private String senha;

    public RegisterRequest() {}

    public RegisterRequest(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

