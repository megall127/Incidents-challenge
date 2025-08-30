package com.example.crud.controller;

import com.example.crud.model.Incident;
import com.example.crud.repository.IncidentRepository;
import com.example.crud.dto.IncidentRequest;
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

    @GetMapping("/incidents")
    public ResponseEntity<?> getAllIncidents() {
        List<Incident> incidents = incidentRepository.findAll();
        if (incidents.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Não há incidentes cadastrados no sistema");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(incidents);
    }
    
    @GetMapping("/incidents/{id}")
    public ResponseEntity<?> getIncidentById(@PathVariable UUID id) {
        Incident incident = incidentRepository.findById(id).orElse(null);
        
        if (incident == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Incidente não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        
        return ResponseEntity.ok(incident);
    }
    
    @PostMapping("/incidents")
    public ResponseEntity<?> createIncident(@Valid @RequestBody IncidentRequest request) {
        Incident incident = new Incident();
        incident.setTitulo(request.getTitulo());
        incident.setDescricao(request.getDescricao());
        incident.setPrioridade(request.getPrioridade() != null ? request.getPrioridade() : Priority.MEDIA);
        incident.setStatus(request.getStatus() != null ? request.getStatus() : Status.ABERTA);
        incident.setResponsavelEmail(request.getResponsavelEmail());
        incident.setTags(request.getTags());
        
        Incident savedIncident = incidentRepository.save(incident);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIncident);
    }
}
