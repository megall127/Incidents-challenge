package com.example.crud.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(description = "Dados para criação de comentário")
public class CommentRequest {
    
    @Schema(description = "ID do incidente", example = "123e4567-e89b-12d3-a456-426614174000")
    @NotNull(message = "ID do incidente é obrigatório")
    private UUID incidentId;
    
    @Schema(description = "Nome do autor do comentário", example = "Leandro Wilker")
    @NotBlank(message = "Autor é obrigatório")
    @Size(max = 255, message = "Autor deve ter no máximo 255 caracteres")
    private String autor;
    
    @Schema(description = "Mensagem do comentário", example = "Este incidente foi resolvido.")
    @NotBlank(message = "Mensagem é obrigatória")
    @Size(min = 1, max = 2000, message = "Mensagem deve ter entre 1 e 2000 caracteres")
    private String mensagem;
    
    public CommentRequest() {}
    
    public CommentRequest(UUID incidentId, String autor, String mensagem) {
        this.incidentId = incidentId;
        this.autor = autor;
        this.mensagem = mensagem;
    }
    
    public UUID getIncidentId() {
        return incidentId;
    }
    
    public void setIncidentId(UUID incidentId) {
        this.incidentId = incidentId;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public String getMensagem() {
        return mensagem;
    }
    
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
