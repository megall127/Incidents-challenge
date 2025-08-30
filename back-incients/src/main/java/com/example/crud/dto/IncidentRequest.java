package com.example.crud.dto;

import com.example.crud.enums.Priority;
import com.example.crud.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "Dados para criação de incidente")
public class IncidentRequest {
    
    @Schema(description = "Título do incidente", example = "Sistema fora do ar")
    @NotBlank(message = "Título é obrigatório")
    @Size(min = 5, max = 120, message = "Título deve ter entre 5 e 120 caracteres")
    private String titulo;
    
    @Schema(description = "Descrição detalhada do incidente", example = "O sistema principal está apresentando lentidão e timeouts")
    @Size(max = 5000, message = "Descrição deve ter no máximo 5000 caracteres")
    private String descricao;
    
    @Schema(description = "Prioridade do incidente", example = "ALTA")
    private Priority prioridade;
    
    @Schema(description = "Status inicial do incidente", example = "ABERTO")
    private Status status;
    
    @Schema(description = "Email do responsável", example = "leandro@gmail.com")
    @NotBlank(message = "Email do responsável é obrigatório")
    @Email(message = "Email deve ser válido")
    private String responsavelEmail;
    
    @Schema(description = "Tags para categorização", example = "[\"sistema tal\", \"performance tal\"]")
    private List<String> tags;
    
    public IncidentRequest() {}
    
    public IncidentRequest(String titulo, String descricao, Priority prioridade, Status status, String responsavelEmail, List<String> tags) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.status = status;
        this.responsavelEmail = responsavelEmail;
        this.tags = tags;
    }
    
    // Getters e Setters
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public Priority getPrioridade() {
        return prioridade;
    }
    
    public void setPrioridade(Priority prioridade) {
        this.prioridade = prioridade;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public String getResponsavelEmail() {
        return responsavelEmail;
    }
    
    public void setResponsavelEmail(String responsavelEmail) {
        this.responsavelEmail = responsavelEmail;
    }
    
    public List<String> getTags() {
        return tags;
    }
    
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
