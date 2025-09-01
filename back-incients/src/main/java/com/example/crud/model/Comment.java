package com.example.crud.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments")
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(name = "incident_id", nullable = false)
    private UUID incidentId;
    
    @Column(nullable = false)
    private String autor;
    
    @Column(nullable = false, length = 2000)
    private String mensagem;
    
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incident_id", insertable = false, updatable = false)
    private Incident incident;
    
    public Comment() {
        this.dataCriacao = LocalDateTime.now();
    }
    
    public Comment(UUID incidentId, String autor, String mensagem) {
        this.incidentId = incidentId;
        this.autor = autor;
        this.mensagem = mensagem;
        this.dataCriacao = LocalDateTime.now();
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
    
    public Incident getIncident() {
        return incident;
    }
    
    public void setIncident(Incident incident) {
        this.incident = incident;
    }
}
