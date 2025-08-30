package com.example.crud.dto;

import com.example.crud.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para atualização de status do incidente")
public class StatusUpdateRequest {
    
    @Schema(description = "Novo status do incidente", example = "RESOLVIDA")
    @NotNull(message = "Status é obrigatório")
    private Status status;
    
    public StatusUpdateRequest() {}
    
    public StatusUpdateRequest(Status status) {
        this.status = status;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
}
