package com.example.crud.controller;

import com.example.crud.model.Incident;
import com.example.crud.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
