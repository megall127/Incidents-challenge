package com.example.crud.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta de erro")
public class ErrorResponse {
    
    @Schema(description = "Tipo do erro", example = "Não autenticado")
    private String error;
    
    @Schema(description = "Mensagem de erro", example = "Token JWT necessário")
    private String message;
    
    @Schema(description = "Código de status HTTP", example = "401")
    private int status;
    
    @Schema(description = "Caminho da requisição", example = "/incidents")
    private String path;

    public ErrorResponse() {}

    public ErrorResponse(String error, String message, int status, String path) {
        this.error = error;
        this.message = message;
        this.status = status;
        this.path = path;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
