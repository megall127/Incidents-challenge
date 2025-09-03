package com.example.crud.controller;

import com.example.crud.model.Incident;
import com.example.crud.model.Comment;
import com.example.crud.repository.IncidentRepository;
import com.example.crud.repository.CommentRepository;
import com.example.crud.dto.IncidentRequest;
import com.example.crud.dto.IncidentUpdateRequest;
import com.example.crud.dto.IncidentResponse;
import com.example.crud.dto.CommentRequest;
import com.example.crud.dto.CommentResponse;
import com.example.crud.dto.StatusUpdateRequest;
import com.example.crud.enums.Priority;
import com.example.crud.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class IncidentController {

    @Autowired
    private IncidentRepository incidentRepository;
    
    @Autowired
    private CommentRepository commentRepository;

    private IncidentResponse convertToIncidentResponse(Incident incident) {
        List<CommentResponse> commentResponses = null;
        
        List<Comment> comments = commentRepository.findByIncidentIdOrderByDataCriacaoDesc(incident.getId());
        
        if (comments != null && !comments.isEmpty()) {
            commentResponses = comments.stream()
                .map(comment -> new CommentResponse(
                    comment.getId(),
                    comment.getIncidentId(),
                    comment.getAutor(),
                    comment.getMensagem(),
                    comment.getDataCriacao()
                ))
                .toList();
        }
        
        return new IncidentResponse(
            incident.getId(),
            incident.getTitulo(),
            incident.getDescricao(),
            incident.getPrioridade(),
            incident.getStatus(),
            incident.getResponsavelEmail(),
            incident.getTags(),
            incident.getDataAbertura(),
            incident.getDataAtualizacao(),
            commentResponses
        );
    }

    @GetMapping("/incidents")
    public ResponseEntity<?> getAllIncidents() {
        List<Incident> incidents = incidentRepository.findAll();
        System.out.println( "incidents: " + incidents);
        if (incidents.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Não há incidentes cadastrados no sistema");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        List<IncidentResponse> incidentResponses = incidents.stream()
            .map(this::convertToIncidentResponse)
            .toList();
        
        return ResponseEntity.ok(incidentResponses);
    }
    
    @GetMapping("/incidents/{id}")
    public ResponseEntity<?> getIncidentById(@PathVariable UUID id) {
        Incident incident = incidentRepository.findById(id).orElse(null);
        
        if (incident == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Incidente não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        IncidentResponse incidentResponse = convertToIncidentResponse(incident);
        return ResponseEntity.ok(incidentResponse);
    }
    
    @PostMapping("/incidents")
    public ResponseEntity<?> createIncident(@Valid @RequestBody IncidentRequest request) {
        Incident incident = new Incident();
        incident.setTitulo(request.getTitulo());
        incident.setDescricao(request.getDescricao());
        incident.setPrioridade(request.getPrioridade() != null ? request.getPrioridade() : Priority.MEDIA);
        incident.setStatus(request.getStatus() != null ? request.getStatus() : Status.ABERTO);
        incident.setResponsavelEmail(request.getResponsavelEmail());
        incident.setTags(request.getTags());
        
        Incident savedIncident = incidentRepository.save(incident);
        
        IncidentResponse incidentResponse = convertToIncidentResponse(savedIncident);
        return ResponseEntity.status(HttpStatus.CREATED).body(incidentResponse);
    }
    
    @PutMapping("/incidents/{id}")
    public ResponseEntity<?> updateIncident(@PathVariable UUID id, @Valid @RequestBody IncidentUpdateRequest request) {
        Incident existingIncident = incidentRepository.findById(id).orElse(null);
        
        if (existingIncident == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Incidente não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        if (request.getTitulo() != null) {
            existingIncident.setTitulo(request.getTitulo());
        }
        if (request.getDescricao() != null) {
            existingIncident.setDescricao(request.getDescricao());
        }
        if (request.getPrioridade() != null) {
            existingIncident.setPrioridade(request.getPrioridade());
        }
        if (request.getStatus() != null) {
            existingIncident.setStatus(request.getStatus());
        }
        if (request.getResponsavelEmail() != null) {
            existingIncident.setResponsavelEmail(request.getResponsavelEmail());
        }
        if (request.getTags() != null) {
            existingIncident.setTags(request.getTags());
        }
        
        Incident updatedIncident = incidentRepository.save(existingIncident);
        
        IncidentResponse incidentResponse = convertToIncidentResponse(updatedIncident);
        return ResponseEntity.ok(incidentResponse);
    }
    
    @DeleteMapping("/incidents/{id}")
    public ResponseEntity<?> deleteIncident(@PathVariable UUID id) {
        System.out.println("=== DELETE Incident Debug ===");
        System.out.println("Deleting incident with ID: " + id);
        
        Incident existingIncident = incidentRepository.findById(id).orElse(null);
        
        if (existingIncident == null) {
            System.out.println("Incident not found");
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Incidente não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        System.out.println("Found incident: " + existingIncident.getTitulo());
        System.out.println("Deleting incident...");
        
        incidentRepository.delete(existingIncident);
        
        System.out.println("Incident deleted successfully");
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Incidente deletado com sucesso");
        response.put("deletedIncident", convertToIncidentResponse(existingIncident));
        
        System.out.println("=== End DELETE Incident Debug ===");
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/incidents/{id}/comments")
    public ResponseEntity<?> addCommentToIncident(@PathVariable UUID id, @Valid @RequestBody CommentRequest request) {
        Incident incident = incidentRepository.findById(id).orElse(null);
        
        if (incident == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Incidente não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        Comment comment = new Comment();
        comment.setIncidentId(id);
        comment.setAutor(request.getAutor());
        comment.setMensagem(request.getMensagem());
        
        Comment savedComment = commentRepository.save(comment);
        
        CommentResponse commentResponse = new CommentResponse(
            savedComment.getId(),
            savedComment.getIncidentId(),
            savedComment.getAutor(),
            savedComment.getMensagem(),
            savedComment.getDataCriacao()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponse);
    }
    
    @GetMapping("/incidents/{id}/comments")
    public ResponseEntity<?> getCommentsByIncidentId(@PathVariable UUID id) {
        Incident incident = incidentRepository.findById(id).orElse(null);
        
        if (incident == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Incidente não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        List<Comment> comments = commentRepository.findByIncidentIdOrderByDataCriacaoDesc(id);
        
        if (comments.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Nenhum comentário encontrado para este incidente");
            response.put("incidentId", id);
            return ResponseEntity.ok(response);
        }
        
        List<CommentResponse> commentResponses = comments.stream()
            .map(comment -> new CommentResponse(
                comment.getId(),
                comment.getIncidentId(),
                comment.getAutor(),
                comment.getMensagem(),
                comment.getDataCriacao()
            ))
            .toList();
        
        return ResponseEntity.ok(commentResponses);
    }
    
    @PatchMapping("/incidents/{id}/status")
    public ResponseEntity<?> updateIncidentStatus(@PathVariable UUID id, @Valid @RequestBody StatusUpdateRequest request) {
       
        Incident existingIncident = incidentRepository.findById(id).orElse(null);
        
        if (existingIncident == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Incidente não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
       
        Status oldStatus = existingIncident.getStatus();
        existingIncident.setStatus(request.getStatus());
        
    
        Incident updatedIncident = incidentRepository.save(existingIncident);
    
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Status do incidente atualizado com sucesso");
        response.put("incidentId", id);
        response.put("oldStatus", oldStatus);
        response.put("newStatus", request.getStatus());
        response.put("incident", convertToIncidentResponse(updatedIncident));
        
        return ResponseEntity.ok(response);
    }
}
