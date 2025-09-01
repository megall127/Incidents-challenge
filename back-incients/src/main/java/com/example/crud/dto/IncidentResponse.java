package com.example.crud.dto;

import com.example.crud.enums.Priority;
import com.example.crud.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "Resposta de incidente")
public class IncidentResponse {
    
    @Schema(description = "ID do incidente", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;
    
    @Schema(description = "Título do incidente", example = "Sistema fora do ar")
    private String titulo;
    
    @Schema(description = "Descrição do incidente", example = "O sistema principal está apresentando lentidão")
    private String descricao;
    
    @Schema(description = "Prioridade do incidente", example = "ALTA")
    private Priority prioridade;
    
    @Schema(description = "Status do incidente", example = "ABERTO")
    private Status status;
    
    @Schema(description = "Email do responsável", example = "joao@empresa.com")
    private String responsavelEmail;
    
    @Schema(description = "Tags do incidente")
    private List<String> tags;
    
    @Schema(description = "Data de abertura", example = "2024-01-15T10:30:00")
    private LocalDateTime dataAbertura;
    
    @Schema(description = "Data de atualização", example = "2024-01-15T10:30:00")
    private LocalDateTime dataAtualizacao;
    
    @Schema(description = "Comentários do incidente")
    private List<CommentResponse> comments;
    
    public IncidentResponse() {}
    
    public IncidentResponse(UUID id, String titulo, String descricao, Priority prioridade, 
                           Status status, String responsavelEmail, List<String> tags, 
                           LocalDateTime dataAbertura, LocalDateTime dataAtualizacao, 
                           List<CommentResponse> comments) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.status = status;
        this.responsavelEmail = responsavelEmail;
        this.tags = tags;
        this.dataAbertura = dataAbertura;
        this.dataAtualizacao = dataAtualizacao;
        this.comments = comments;
    }
    
    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public Priority getPrioridade() { return prioridade; }
    public void setPrioridade(Priority prioridade) { this.prioridade = prioridade; }
    
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    
    public String getResponsavelEmail() { return responsavelEmail; }
    public void setResponsavelEmail(String responsavelEmail) { this.responsavelEmail = responsavelEmail; }
    
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    
    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; }
    
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
    
    public List<CommentResponse> getComments() { return comments; }
    public void setComments(List<CommentResponse> comments) { this.comments = comments; }
}
