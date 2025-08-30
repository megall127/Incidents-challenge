package com.example.crud.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Resposta de comentário")
public class CommentResponse {
    
    @Schema(description = "ID do comentário", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;
    
    @Schema(description = "ID do incidente", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID incidentId;
    
    @Schema(description = "Nome do autor", example = "João Silva")
    private String autor;
    
    @Schema(description = "Mensagem do comentário", example = "Este incidente foi resolvido.")
    private String mensagem;
    
    @Schema(description = "Data de criação", example = "2024-01-15T10:30:00")
    private LocalDateTime dataCriacao;
    
    public CommentResponse() {}
    
    public CommentResponse(UUID id, UUID incidentId, String autor, String mensagem, LocalDateTime dataCriacao) {
        this.id = id;
        this.incidentId = incidentId;
        this.autor = autor;
        this.mensagem = mensagem;
        this.dataCriacao = dataCriacao;
    }
    
    // Getters e Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
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
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
