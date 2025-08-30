package com.example.crud.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta de cadastro de usuário")
public class RegisterResponse {
    
    @Schema(description = "Mensagem de confirmação", example = "Usuário cadastrado com sucesso!")
    private String message;

    public RegisterResponse() {}

    public RegisterResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
