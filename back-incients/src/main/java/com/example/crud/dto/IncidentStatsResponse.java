package com.example.crud.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

@Schema(description = "Estatísticas agregadas dos incidentes")
public class IncidentStatsResponse {
    
    @Schema(description = "Total de incidentes", example = "25")
    private Long totalIncidents;
    
    @Schema(description = "Contagem por status", example = "{\"ABERTA\": 10, \"EM_ANDAMENTO\": 8, \"RESOLVIDA\": 5, \"CANCELADA\": 2}")
    private Map<String, Long> incidentsByStatus;
    
    @Schema(description = "Contagem por prioridade", example = "{\"BAIXA\": 5, \"MEDIA\": 12, \"ALTA\": 6, \"CRITICA\": 2}")
    private Map<String, Long> incidentsByPriority;
    
    @Schema(description = "Contagem por responsável", example = "{\"joao@empresa.com\": 8, \"maria@empresa.com\": 12, \"pedro@empresa.com\": 5}")
    private Map<String, Long> incidentsByResponsible;
    
    @Schema(description = "Total de comentários", example = "45")
    private Long totalComments;
    
    @Schema(description = "Média de comentários por incidente", example = "1.8")
    private Double averageCommentsPerIncident;
    
    public IncidentStatsResponse() {}
    
    public IncidentStatsResponse(Long totalIncidents, Map<String, Long> incidentsByStatus, 
                                Map<String, Long> incidentsByPriority, Map<String, Long> incidentsByResponsible,
                                Long totalComments, Double averageCommentsPerIncident) {
        this.totalIncidents = totalIncidents;
        this.incidentsByStatus = incidentsByStatus;
        this.incidentsByPriority = incidentsByPriority;
        this.incidentsByResponsible = incidentsByResponsible;
        this.totalComments = totalComments;
        this.averageCommentsPerIncident = averageCommentsPerIncident;
    }
    
    public Long getTotalIncidents() {
        return totalIncidents;
    }
    
    public void setTotalIncidents(Long totalIncidents) {
        this.totalIncidents = totalIncidents;
    }
    
    public Map<String, Long> getIncidentsByStatus() {
        return incidentsByStatus;
    }
    
    public void setIncidentsByStatus(Map<String, Long> incidentsByStatus) {
        this.incidentsByStatus = incidentsByStatus;
    }
    
    public Map<String, Long> getIncidentsByPriority() {
        return incidentsByPriority;
    }
    
    public void setIncidentsByPriority(Map<String, Long> incidentsByPriority) {
        this.incidentsByPriority = incidentsByPriority;
    }
    
    public Map<String, Long> getIncidentsByResponsible() {
        return incidentsByResponsible;
    }
    
    public void setIncidentsByResponsible(Map<String, Long> incidentsByResponsible) {
        this.incidentsByResponsible = incidentsByResponsible;
    }
    
    public Long getTotalComments() {
        return totalComments;
    }
    
    public void setTotalComments(Long totalComments) {
        this.totalComments = totalComments;
    }
    
    public Double getAverageCommentsPerIncident() {
        return averageCommentsPerIncident;
    }
    
    public void setAverageCommentsPerIncident(Double averageCommentsPerIncident) {
        this.averageCommentsPerIncident = averageCommentsPerIncident;
    }
}
